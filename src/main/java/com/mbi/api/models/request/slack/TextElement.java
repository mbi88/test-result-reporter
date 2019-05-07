package com.mbi.api.models.request.slack;

/**
 * ButtonElement request model.
 */
public class TextElement extends Element {

    private String type;

    private String text;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
