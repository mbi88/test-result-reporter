package com.mbi.api.entities.testrun;

import javax.persistence.*;

/**
 * Test case entity.
 */
@Entity
@Table(name = "test_cases")
@SequenceGenerator(name = "test_case_id_seq", sequenceName = "test_case_id_seq", allocationSize = 1)
public class TestCaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_case_id_seq")
    private Integer id;

    private String name;

    private int duration;

    private String status;

    @Column(length = 10_000)
    private String exception;

    private String className;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_run_id")
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(final String exception) {
        this.exception = exception;
    }

    public TestRunEntity getTestRunEntity() {
        return testRunEntity;
    }

    public void setTestRunEntity(final TestRunEntity testRunEntity) {
        this.testRunEntity = testRunEntity;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }
}
