package com.SmartLogix.Controller;

import com.SmartLogix.Model.Inventory;
import com.SmartLogix.Service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping("/{productoCodigo}/{almacenCodigo}")
    public ResponseEntity<Inventory> getStock(@PathVariable String productoCodigo, @PathVariable String almacenCodigo) {
        return ResponseEntity.ok(service.getStock(productoCodigo, almacenCodigo));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getAllStock(){
        return ResponseEntity.ok(service.getAllStock());
    }

    @PostMapping("/add")
    public ResponseEntity<Inventory> addProduct(@RequestBody Inventory inventory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addProduct(inventory));
    }

    @PostMapping("/bulk-add")
    public ResponseEntity<List<Inventory>> addProducts(@RequestBody List<Inventory> products){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addProducts(products));
    }

    @PostMapping("/update")
    public ResponseEntity<Inventory> updateStock(@RequestBody Inventory request) {
        return ResponseEntity.ok(service.updateStock(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateById(@PathVariable Long id, @RequestBody Inventory request) {
        return ResponseEntity.ok(service.updateById(id, request));
    }
}