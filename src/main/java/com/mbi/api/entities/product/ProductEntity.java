package com.mbi.api.entities.product;

import com.mbi.api.entities.testrun.TestRunEntity;

import javax.persistence.*;

/**
 * Product entity.
 */
@Entity
@Table(name = "products")
@SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    private Integer id;

    private String name;

    @OneToOne(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TestRunEntity testRunEntity;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public TestRunEntity getTestRunEntity() {
        return testRunEntity;
    }

    public void setTestRunEntity(final TestRunEntity testRunEntity) {
        this.testRunEntity = testRunEntity;
    }
}
