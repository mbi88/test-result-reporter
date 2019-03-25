package com.mbi.api.entities.testrun;

import javax.persistence.*;

@Entity
@Table(name = "methods")
@SequenceGenerator(name = "method_id_seq", sequenceName = "method_id_seq", allocationSize = 1)
public class MethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "method_id_seq")
    private Long id;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="class_id")
    private ClassEntity classEntity;

    private String name;

    private String duration;

    private String status;

    @Column(length=10000)
    private String exception;

    public MethodEntity() {
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

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
