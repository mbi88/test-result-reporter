package com.mbi.api.models.request.report;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Product request model.
 */
public class ProductModel {

    @NotNull
    @Length(min = 2, max = 30)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
