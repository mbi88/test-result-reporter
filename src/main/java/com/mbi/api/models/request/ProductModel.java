package com.mbi.api.models.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class ProductModel {

    @NotNull
    @Length(min = 2, max = 30)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
