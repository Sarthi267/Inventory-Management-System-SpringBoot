package com.github.sarthi267.inventorymanagementsystem;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "items")
public class Item {
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Setter
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
    @Setter
    @Positive(message = "Price must be positive")
    private double price;

    public Item() {

    }

    public Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;


}
