package com.github.sarthi267.inventorymanagementsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }
    public void deleteItem(Long id) {
        if(!itemRepository.existsById(id)){
            throw new RuntimeException("Item does not exist with id: " + id);
        }
        itemRepository.deleteById(id);
    }
    public Item updateItem(Long id, Item updatedItem) {
        if(!itemRepository.existsById(id)){
            throw new RuntimeException("Item does not exist with id: " + id);
        }
        updatedItem.setId(id);
        return itemRepository.save(updatedItem);
    }

}
