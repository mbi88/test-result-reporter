package com.mbi.api.entities.testrun;

import javax.persistence.*;
import java.util.Set;

/**
 * Class entity.
 */
@Entity
@Table(name = "classes")
@SequenceGenerator(name = "class_id_seq", sequenceName = "class_id_seq", allocationSize = 1)
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "class_id_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id")
    private TestEntity testEntity;

    @OneToMany(mappedBy = "classEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<MethodEntity> methods;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public TestEntity getTestEntity() {
        return testEntity;
    }

    public void setTestEntity(final TestEntity testEntity) {
        this.testEntity = testEntity;
    }

    public Set<MethodEntity> getMethods() {
        return methods;
    }

    public void setMethods(final Set<MethodEntity> methods) {
        this.methods = methods;
    }
}
