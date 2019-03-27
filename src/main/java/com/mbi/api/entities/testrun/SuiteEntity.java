package com.mbi.api.entities.testrun;

import javax.persistence.*;
import java.util.Set;

/**
 * Suite entity.
 */
@Entity
@Table(name = "suites")
@SequenceGenerator(name = "suite_id_seq", sequenceName = "suite_id_seq", allocationSize = 1)
public class SuiteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suite_id_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_run_id")
    private TestRunEntity testRunEntity;

    private String name;

    private String duration;

    @OneToMany(mappedBy = "suiteEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TestEntity> tests;

    public TestRunEntity getTestRunEntity() {
        return testRunEntity;
    }

    public void setTestRunEntity(final TestRunEntity testRunEntity) {
        this.testRunEntity = testRunEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Set<TestEntity> getTests() {
        return tests;
    }

    public void setTests(final Set<TestEntity> tests) {
        this.tests = tests;
    }
}
