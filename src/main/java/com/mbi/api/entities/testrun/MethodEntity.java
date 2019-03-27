package com.mbi.api.entities.testrun;

import javax.persistence.*;

/**
 * Method entity.
 */
@Entity
@Table(name = "methods")
@SequenceGenerator(name = "method_id_seq", sequenceName = "method_id_seq", allocationSize = 1)
public class MethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "method_id_seq")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    private String name;

    private String duration;

    private String status;

    @Column(length = 10000)
    private String exception;

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

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(final ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
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
}
