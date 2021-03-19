package com.mbi.api.models.request.report;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Product request model.
 */
public class ProductModel {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
