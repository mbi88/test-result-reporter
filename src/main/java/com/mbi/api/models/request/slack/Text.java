package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Text request model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Text {

    private String type;

    private String text;

    private boolean emoji;

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

    public boolean isEmoji() {
        return emoji;
    }

    public void setEmoji(final boolean emoji) {
        this.emoji = emoji;
    }
}
