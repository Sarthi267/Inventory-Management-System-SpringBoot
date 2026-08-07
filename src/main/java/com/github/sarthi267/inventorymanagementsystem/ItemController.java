package com.github.sarthi267.inventorymanagementsystem;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
            (produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
    @PostMapping
    public Item addItem(@Valid @RequestBody Item item) {
        return itemService.addItem(item);
    }
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }
    @GetMapping("/{id}")
    public Item findById(@PathVariable Long id) {
        return itemService.findById(id);
    }

    @GetMapping("/")
    public String home() {
        return "Inventory API is running";
    }


}
