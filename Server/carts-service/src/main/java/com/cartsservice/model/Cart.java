package com.cartsservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cart;
    private Double total_ammount;
    @ElementCollection
    @CollectionTable(name = "products_ids", joinColumns = @JoinColumn(name = "id_cart"))
    @Column(name = "id_product")
    private List<Long> product_ids;
}
