package com.github.sarthi267.inventorymanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.oauth2.client.autoconfigure.servlet.OAuth2ClientWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(controllers = ItemController.class,
        excludeAutoConfiguration = { OAuth2ClientWebSecurityAutoConfiguration.class }
)
@ActiveProfiles("test")
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    @WithMockUser
            (username = "admin", password = "password123", roles = {"ADMIN"})
    void shouldGetAllItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser
    void shouldReturnItemList() throws Exception {
        when(itemService.getAllItems()).thenReturn(List.of(new Item("laptop", 5, 999.99)));
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("laptop"))
                .andExpect(jsonPath("$[0].quantity").value(5))
                .andExpect(jsonPath("$[0].price").value(999.99));
    }
    @Test
    @WithMockUser
    void shouldCreateItem() throws Exception {
        String jsonBody = """
                { 
                    "name": "laptop", 
                    "quantity":5,
                    "price": 999.99 
                }
                """;

        mockMvc.perform(post("/items")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser
    void shouldReturn400WhenItemInvalid() throws Exception {
        String invalidJsonBody = """
                {
                    "name": "",
                    "quantity": -1,
                    "price": -999.99
                }
        """;
        mockMvc.perform(post("/items")
                .with(csrf())
                .contentType(APPLICATION_JSON)
                .content(invalidJsonBody))
                .andExpect(status().isBadRequest());

    }
    @Test
    @WithMockUser
    void shouldReturn404WhenItemNotFound() throws Exception {
        when(itemService.findById(99L)).
                thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        mockMvc.perform(get("/items/{id}", 99L))
                .andExpect(status().isNotFound());
    }
}
