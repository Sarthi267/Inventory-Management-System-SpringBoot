package com.github.sarthi267.inventorymanagementsystem;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    @Transactional
    void shouldGetItemAfterCreation() throws Exception {
        String jsonBody = """
                { 
                    "name": "laptop", 
                    "quantity":5,
                    "price": 999.99 
                }
                """;

        mockMvc.perform(post("/items")
                        .with(user("admin").password("password123").roles("ADMIN"))
                        .with(csrf())
                        .contentType(APPLICATION_JSON)
                        .content(jsonBody))
                        .andExpect(status().isOk());
        mockMvc.perform(get("/items")
                .with(user("admin").password("password123").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("laptop"))
                .andExpect(jsonPath("$[0].quantity").value(5))
                .andExpect(jsonPath("$[0].price").value(999.99));
    }
   @Test
    void shouldFailValidationWhenInvalidFieldsProvided() throws Exception {
        String invalidJsonBody = """
                {
                    "name": "",
                    "quantity":-5,
                    "price": -999.99
                }
        """;

        mockMvc.perform(post("/items")
                .with(user("admin").password("password123").roles("ADMIN"))
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(invalidJsonBody))
                .andExpect(status().isBadRequest());


    }
    @Test
    void shouldRollbackDatabaseChangesWhenServiceThrowsException() throws Exception {
        long initialCount = itemRepository.count();

        Item invalidItem = new Item();
        invalidItem.setId(1L);
        invalidItem.setName("");
        invalidItem.setQuantity(-5);
        invalidItem.setPrice(-999.99);

        assertThrows(Exception.class, () -> {
            itemService.saveItem(1L, invalidItem);
        });
        assertThat(itemRepository.count()).isEqualTo(initialCount);
    }
    @Test
    void shouldReturn403ForbiddenWhenNonAdminTriesToCreateItem() throws Exception {
        String jsonBody = """
                {
                    "name": "laptop",
                    "quantity":5,
                    "price": 999.99
                }
        """;
        mockMvc.perform(post("/items")
            .with(user("user").password("password321").roles("USER"))
            .with(csrf())
            .contentType(APPLICATION_JSON)
            .content(jsonBody))
        .andExpect(status().isForbidden());

    }
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
        //Value source takes one variable type per execution, CSV does multiple
    void shouldFailValidationWhenNameIsBlank(String invalidName) throws Exception {
        String invalidJsonBody = """
                {
                    "name": "%s",
                    "quantity":5,
                    "price": 999.99
                }
        """.formatted(invalidName);

        mockMvc.perform(post("/items")
                        .with(user("admin").password("password123").roles("ADMIN"))
                        .with(csrf())
                        .contentType(APPLICATION_JSON)
                        .content(invalidJsonBody))
                .andExpect(status().isBadRequest());


    }


}
