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
    private Integer id;

    private int total;

    private int passed;

    private int failed;

    private int skipped;

    private int ignored;

    private int duration;

    private boolean successful;

    @OneToMany(mappedBy = "testRunEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TestCaseEntity> testCases;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(final int total) {
        this.total = total;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(final int passed) {
        this.passed = passed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(final int failed) {
        this.failed = failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(final int skipped) {
        this.skipped = skipped;
    }

    public int getIgnored() {
        return ignored;
    }

    public void setIgnored(final int ignored) {
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

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(final boolean successful) {
        this.successful = successful;
    }

    public Set<TestCaseEntity> getTestCases() {
        return testCases;
    }

    public void setTestCases(final Set<TestCaseEntity> testCases) {
        this.testCases = testCases;
    }
}
