package com.github.sarthi267.inventorymanagementsystem;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/inventory")
    public String view(Model model) {
        List<Item> itemList = itemService.getAllItems();
        model.addAttribute("items", itemList);
        return "inventory";

    }
    @PostMapping("/items/add")
    public String addItem(Item item) {
        itemService.addItem(item);
        return "redirect:/inventory";
    }
    @PostMapping("/items/delete/{id}")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return "redirect:/inventory";
    }
    @GetMapping("/items/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "edit";
    }
    @PostMapping("/items/edit/{id}")
    public String editItem(@PathVariable Long id, Item item) {
        itemService.saveItem(id, item);
        return "redirect:/inventory";
    }
    @GetMapping("/")
    public String root(){
        return "redirect:/inventory";
    }
}
