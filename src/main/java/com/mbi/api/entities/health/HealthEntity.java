package com.mbi.api.entities.health;

/**
 * Health entity.
 */
public class HealthEntity {

    private String status;
    private String version;

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }
}
