package com.SmartLogix.Service;

import com.SmartLogix.Model.Inventory;
import com.SmartLogix.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;

    public Inventory getStock(String productoCodigo, String almacenCodigo) {
        return repository.findByProductoCodigoAndAlmacenCodigo(productoCodigo, almacenCodigo)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró inventario para el producto: " + productoCodigo + " en el almacén: " + almacenCodigo));
    }

    public List<Inventory> getAllStock() {
        return repository.findAll();
    }

    public Inventory addProduct(Inventory inventory) {
        if (inventory.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        return repository.save(inventory);
    }

    public List<Inventory> addProducts(List<Inventory> products) {
        return repository.saveAll(products);
    }

    public Inventory updateStock(Inventory request) {
        if (request.getStock() < 0) {
            throw new IllegalArgumentException("El stock a actualizar no puede ser negativo");
        }
        Inventory inventory = repository.findByProductoCodigoAndAlmacenCodigo(request.getProductoCodigo(), request.getAlmacenCodigo())
                .orElse(new Inventory());

        inventory.setProductoCodigo(request.getProductoCodigo());
        inventory.setAlmacenCodigo(request.getAlmacenCodigo());
        inventory.setStock(request.getStock());

        return repository.save(inventory);
    }

    public Inventory updateById(Long id, Inventory request) {
        if (request.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontro inventario con id: " + id));

        inventory.setProductoCodigo(request.getProductoCodigo());
        inventory.setAlmacenCodigo(request.getAlmacenCodigo());
        inventory.setStock(request.getStock());

        return repository.save(inventory);
    }
}