package com.github.sarthi267.inventorymanagementsystem;



import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public List<Item> getAllItems() {
        logger.info("Fetching all items");
        List<Item> items = itemRepository.findAllWithCategory();
        for(Item item : items){
           String categoryName = item.getCategory() != null ? item.getCategory().getName() : "none";
            logger.debug("{} - {}", item.getName(), categoryName);
       }
       return items;
    }
    @Transactional
    public Item addItem(Item item) {
        logger.info("Adding new item: {}", item.getName());
        return itemRepository.save(item);
    }
    public void deleteItem(Long id) {
        if(!itemRepository.existsById(id)){
            logger.warn("Failed to delete. Item with id {} does not exist", id);
            throw new RuntimeException("Item does not exist with id: " + id);
        }
        logger.info("Deleting item with id {}", id);
        itemRepository.deleteById(id);
    }
    public Item updateItem(Long id, Item updatedItem) {
        if(!itemRepository.existsById(id)){
            logger.warn("Failed to update. Item with id {} does not exist", id);
            throw new RuntimeException("Item does not exist with id: " + id);
        }
        updatedItem.setId(id);
        logger.info("Updating item with id {}", id);
        return itemRepository.save(updatedItem);
    }
    public Item findById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> {
            logger.error("Item not found with id {} ", id);
            return new RuntimeException("Item not found with id: " + id);
        });
        logger.debug("Item found with id {} ", id);
        if(item.getQuantity() <= 1){
            logger.warn("Item stock is low: {} (Quantity: {})", item.getName(), item.getQuantity());
        }
        return item;
    }
    public Item saveItem(Long id, Item item) {
        return itemRepository.save(item);
    }




}
