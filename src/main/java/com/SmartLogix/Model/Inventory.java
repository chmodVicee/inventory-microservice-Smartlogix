package com.SmartLogix.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productoCodigo;
    private Integer stock;
    private String almacenCodigo;
}
