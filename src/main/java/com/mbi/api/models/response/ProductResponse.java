package com.mbi.api.models.response;


/**
 * Product response model.
 */
public class ProductResponse {

    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
