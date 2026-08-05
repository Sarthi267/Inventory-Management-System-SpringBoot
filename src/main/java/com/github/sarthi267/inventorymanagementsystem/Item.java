package com.github.sarthi267.inventorymanagementsystem;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
    @Positive(message = "Price must be positive")
    private double price;
    public Item() {}

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

public Category getCategory() {
    return category;
}
public void setCategory(Category category) {
    this.category = category;
}
public  Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
    this.name = name;
}
public int getQuantity() {
    return quantity;
}
public void setQuantity(int quantity) {
    this.quantity = quantity;
}
public double getPrice() {
    return price;
}
public void setPrice(double price) {
    this.price = price;
}
}
