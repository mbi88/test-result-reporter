package com.mbi.api.entities.product;

import javax.persistence.*;

@Entity
@Table(name = "products")
@SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    private Long id;

    private String name;

    public ProductEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
