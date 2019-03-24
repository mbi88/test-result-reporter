package com.mbi.api.entities.testrun;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "test_runs")
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)})
@SequenceGenerator(name = "test_run_id_seq", sequenceName = "test_run_id_seq", allocationSize = 1)
public class TestRunEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_run_id_seq")
    private Long id;

    private String name;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private RunResultEntity runResult;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable=false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public TestRunEntity() {
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

    public RunResultEntity getRunResult() {
        return runResult;
    }

    public void setRunResult(RunResultEntity runResult) {
        this.runResult = runResult;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
