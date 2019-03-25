package com.mbi.api.entities.testrun;

import javax.persistence.*;

@Entity
@Table(name = "suites")
@SequenceGenerator(name = "suite_id_seq", sequenceName = "suite_id_seq", allocationSize = 1)
public class SuiteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "suite_id_seq")
    private Long id;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="test_run_id")
    private TestRunEntity testRunEntity;

    private String name;

    private String duration;

    public SuiteEntity() {
    }

    public TestRunEntity getTestRunEntity() {
        return testRunEntity;
    }

    public void setTestRunEntity(TestRunEntity testRunEntity) {
        this.testRunEntity = testRunEntity;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
