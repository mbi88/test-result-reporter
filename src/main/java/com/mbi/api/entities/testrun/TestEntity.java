package com.mbi.api.entities.testrun;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tests")
@SequenceGenerator(name = "test_id_seq", sequenceName = "test_id_seq", allocationSize = 1)
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_id_seq")
    private Long id;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="suite_id")
    private SuiteEntity suiteEntity;

    @OneToMany(mappedBy = "testEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ClassEntity> classes;

    private String name;

    private String duration;

    public TestEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SuiteEntity getSuiteEntity() {
        return suiteEntity;
    }

    public void setSuiteEntity(SuiteEntity suiteEntity) {
        this.suiteEntity = suiteEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Set<ClassEntity> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClassEntity> classes) {
        this.classes = classes;
    }
}
