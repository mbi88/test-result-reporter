package com.mbi.api.models.request.slack;

/**
 * Action request model.
 */
public class Action {

    private String name;

    private String text;

    private String type;

    private String value;

    private String fallback;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(final String fallback) {
        this.fallback = fallback;
    }
}
