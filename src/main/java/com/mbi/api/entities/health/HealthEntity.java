package com.mbi.api.entities.health;

public class HealthEntity {

    private String status;
    private String version;

    public HealthEntity() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
