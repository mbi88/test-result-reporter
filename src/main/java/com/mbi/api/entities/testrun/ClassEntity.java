package com.mbi.api.entities.testrun;

import javax.persistence.*;
import java.util.Set;

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

    public ClassEntity() {
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

    public TestEntity getTestEntity() {
        return testEntity;
    }

    public void setTestEntity(TestEntity testEntity) {
        this.testEntity = testEntity;
    }

    public Set<MethodEntity> getMethods() {
        return methods;
    }

    public void setMethods(Set<MethodEntity> methods) {
        this.methods = methods;
    }
}
