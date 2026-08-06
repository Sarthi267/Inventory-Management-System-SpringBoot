package com.github.sarthi267.inventorymanagementsystem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class InventoryIntegrationTestContainer {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Should spin up Docker container and save item into PostgreSQL database")
    void testSaveAndRetrieveItem(){
        Item newItem = new Item("Laptop", 5, 999.99);
        Item savedItem = itemRepository.save(newItem);

        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Laptop");

        assertThat(postgres.isRunning()).isTrue();
    }

}
