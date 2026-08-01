package com.github.sarthi267.inventorymanagementsystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.category")
    List<Item> findAllWithCategory();
}
