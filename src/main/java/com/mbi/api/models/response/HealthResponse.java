package com.mbi.api.models.response;

/**
 * Health response model.
 */
public class HealthResponse {

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
