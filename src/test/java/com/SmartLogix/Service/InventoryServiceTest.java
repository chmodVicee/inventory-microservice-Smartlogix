package com.SmartLogix.Service;

import com.SmartLogix.Model.Inventory;
import com.SmartLogix.Repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository repository;

    @InjectMocks
    private InventoryService service;

    @Test
    void testGetStock_Found() {
        // Arrange
        Inventory mockInventory = new Inventory(1L, "PROD-1", 100, "ALM-1");
        when(repository.findByProductoCodigoAndAlmacenCodigo("PROD-1", "ALM-1"))
                .thenReturn(Optional.of(mockInventory));

        // Act
        Inventory result = service.getStock("PROD-1", "ALM-1");

        // Assert
        assertNotNull(result);
        assertEquals(100, result.getStock());
    }

    @Test
    void testGetStock_NotFound_ThrowsException() {
        // Arrange
        when(repository.findByProductoCodigoAndAlmacenCodigo("PROD-1", "ALM-1"))
                .thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> service.getStock("PROD-1", "ALM-1"));
        
        assertTrue(exception.getMessage().contains("No se encontró inventario"));
    }

    @Test
    void testGetAllStock() {
        // Arrange
        Inventory inv1 = new Inventory(1L, "P1", 10, "A1");
        Inventory inv2 = new Inventory(2L, "P2", 20, "A2");
        when(repository.findAll()).thenReturn(Arrays.asList(inv1, inv2));

        // Act
        List<Inventory> result = service.getAllStock();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testAddProduct_Success() {
        // Arrange
        Inventory newInventory = new Inventory(null, "P1", 50, "A1");
        Inventory savedInventory = new Inventory(1L, "P1", 50, "A1");
        when(repository.save(newInventory)).thenReturn(savedInventory);

        // Act
        Inventory result = service.addProduct(newInventory);

        // Assert
        assertNotNull(result.getId());
        assertEquals(50, result.getStock());
    }

    @Test
    void testAddProduct_NegativeStock_ThrowsException() {
        // Arrange
        Inventory newInventory = new Inventory(null, "P1", -5, "A1");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> service.addProduct(newInventory));
        
        assertEquals("El stock no puede ser negativo", exception.getMessage());
    }

    @Test
    void testAddProducts_Success() {
        // Arrange
        List<Inventory> products = Arrays.asList(
                new Inventory(null, "P1", 10, "A1"),
                new Inventory(null, "P2", 20, "A2")
        );
        when(repository.saveAll(products)).thenReturn(products);

        // Act
        List<Inventory> result = service.addProducts(products);

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateStock_ExistingInventory_Success() {
        // Arrange
        Inventory request = new Inventory(null, "P1", 100, "A1");
        Inventory existingInv = new Inventory(1L, "P1", 50, "A1");
        
        when(repository.findByProductoCodigoAndAlmacenCodigo("P1", "A1"))
                .thenReturn(Optional.of(existingInv));
        when(repository.save(any(Inventory.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Inventory result = service.updateStock(request);

        // Assert
        assertEquals(100, result.getStock());
        assertEquals(1L, result.getId());
        verify(repository).save(existingInv);
    }

    @Test
    void testUpdateStock_NewInventory_Success() {
        // Arrange
        Inventory request = new Inventory(null, "P2", 200, "A2");
        
        when(repository.findByProductoCodigoAndAlmacenCodigo("P2", "A2"))
                .thenReturn(Optional.empty());
        when(repository.save(any(Inventory.class))).thenAnswer(i -> {
            Inventory inv = (Inventory) i.getArguments()[0];
            inv.setId(2L);
            return inv;
        });

        // Act
        Inventory result = service.updateStock(request);

        // Assert
        assertEquals(200, result.getStock());
        assertEquals(2L, result.getId());
    }

    @Test
    void testUpdateStock_NegativeStock_ThrowsException() {
        // Arrange
        Inventory request = new Inventory(null, "P1", -10, "A1");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> service.updateStock(request));
        
        assertEquals("El stock a actualizar no puede ser negativo", exception.getMessage());
    }
}
