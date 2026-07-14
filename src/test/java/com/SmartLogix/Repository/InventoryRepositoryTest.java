package com.SmartLogix.Repository;

import com.SmartLogix.Model.Inventory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository repository;

    @Test
    void testFindByProductoCodigoAndAlmacenCodigo() {
        // Arrange
        Inventory inventory = new Inventory(null, "PROD-1", 100, "ALM-1");
        repository.save(inventory);

        // Act
        Optional<Inventory> result = repository.findByProductoCodigoAndAlmacenCodigo("PROD-1", "ALM-1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(100, result.get().getStock());
        assertEquals("PROD-1", result.get().getProductoCodigo());
    }

    @Test
    void testFindByProductoCodigoAndAlmacenCodigo_NotFound() {
        // Act
        Optional<Inventory> result = repository.findByProductoCodigoAndAlmacenCodigo("PROD-X", "ALM-X");

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByProductoCodigo() {
        // Arrange
        Inventory inventory = new Inventory(null, "PROD-2", 50, "ALM-2");
        repository.save(inventory);

        // Act
        Optional<Inventory> result = repository.findByProductoCodigo("PROD-2");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(50, result.get().getStock());
        assertEquals("PROD-2", result.get().getProductoCodigo());
    }

    @Test
    void testFindByProductoCodigo_NotFound() {
        // Act
        Optional<Inventory> result = repository.findByProductoCodigo("PROD-Y");

        // Assert
        assertTrue(result.isEmpty());
    }
}
