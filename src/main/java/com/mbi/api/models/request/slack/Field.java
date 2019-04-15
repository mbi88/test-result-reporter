package com.mbi.api.models.request.slack;

/**
 * Field reqquest model.
 */
public class Field {

    private String title;

    private String value;

    private boolean shortField;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public boolean isShortField() {
        return shortField;
    }

    public void setShortField(final boolean shortField) {
        this.shortField = shortField;
    }
}
