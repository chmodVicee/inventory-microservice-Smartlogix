package com.SmartLogix.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InventoryTest {

    @Test
    void testInventoryNoArgsConstructor() {
        // Arrange & Act
        Inventory inventory = new Inventory();

        // Assert
        assertNull(inventory.getId());
        assertNull(inventory.getProductoCodigo());
        assertNull(inventory.getStock());
        assertNull(inventory.getAlmacenCodigo());
    }

    @Test
    void testInventoryAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String productoCodigo = "PROD-1";
        Integer stock = 150;
        String almacenCodigo = "ALM-1";

        // Act
        Inventory inventory = new Inventory(id, productoCodigo, stock, almacenCodigo);

        // Assert
        assertEquals(id, inventory.getId());
        assertEquals(productoCodigo, inventory.getProductoCodigo());
        assertEquals(stock, inventory.getStock());
        assertEquals(almacenCodigo, inventory.getAlmacenCodigo());
    }

    @Test
    void testInventorySettersAndGetters() {
        // Arrange
        Inventory inventory = new Inventory();
        
        // Act
        inventory.setId(2L);
        inventory.setProductoCodigo("PROD-2");
        inventory.setStock(50);
        inventory.setAlmacenCodigo("ALM-2");

        // Assert
        assertEquals(2L, inventory.getId());
        assertEquals("PROD-2", inventory.getProductoCodigo());
        assertEquals(50, inventory.getStock());
        assertEquals("ALM-2", inventory.getAlmacenCodigo());
    }

    @Test
    void testInventoryToString() {
        // Arrange
        Inventory inventory = new Inventory(3L, "PROD-3", 100, "ALM-3");

        // Act
        String toStringResult = inventory.toString();

        // Assert
        // Lombok's @Data generates a toString() method
        assertEquals("Inventory(id=3, productoCodigo=PROD-3, stock=100, almacenCodigo=ALM-3)", toStringResult);
    }
    
    @Test
    void testInventoryEqualsAndHashCode() {
        // Arrange
        Inventory inventory1 = new Inventory(4L, "PROD-4", 200, "ALM-4");
        Inventory inventory2 = new Inventory(4L, "PROD-4", 200, "ALM-4");

        // Act & Assert
        // Lombok's @Data generates equals() and hashCode() methods
        assertEquals(inventory1, inventory2);
        assertEquals(inventory1.hashCode(), inventory2.hashCode());
    }
}
