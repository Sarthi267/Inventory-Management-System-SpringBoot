package com.github.sarthi267.inventorymanagementsystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ItemServiceTest {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void shouldReturnAllItems() {
        Item item = new Item();
        item.setId(1L);
        item.setQuantity(1);
        item.setPrice(1);
        item.setName("test");
        when(itemRepository.findAllWithCategory()).thenReturn(List.of(item));
        List<Item> result = itemService.getAllItems();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("test");
    }
    @Test
    void shouldThrowWhenItemNotFound(){
        when(itemRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> itemService.findById(99L));
    }
}
