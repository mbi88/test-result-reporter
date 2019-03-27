package com.mbi.api.entities.testrun;

import com.mbi.api.entities.product.ProductEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Test run entity.
 */
@Entity
@Table(name = "test_runs")
@SequenceGenerator(name = "test_run_id_seq", sequenceName = "test_run_id_seq", allocationSize = 1)
public class TestRunEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_run_id_seq")
    private Long id;

    private String total;

    private String passed;

    private String failed;

    private String skipped;

    private String ignored;

    private int duration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @OneToMany(mappedBy = "testRunEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SuiteEntity> suites;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(final String total) {
        this.total = total;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(final String passed) {
        this.passed = passed;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(final String failed) {
        this.failed = failed;
    }

    public String getSkipped() {
        return skipped;
    }

    public void setSkipped(final String skipped) {
        this.skipped = skipped;
    }

    public String getIgnored() {
        return ignored;
    }

    public void setIgnored(final String ignored) {
        this.ignored = ignored;
    }

    public Date getCreatedAt() {
        return new Date(createdAt.getTime());
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = new Date(createdAt.getTime());
    }

    public Date getUpdatedAt() {
        return new Date(updatedAt.getTime());
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = new Date(updatedAt.getTime());
    }

    public Set<SuiteEntity> getSuites() {
        return suites;
    }

    public void setSuites(final Set<SuiteEntity> suites) {
        this.suites = suites;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(final ProductEntity product) {
        this.product = product;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
