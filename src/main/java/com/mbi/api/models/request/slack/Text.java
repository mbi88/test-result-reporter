package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Text request model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Text {

    private String type;

    private String text;

    private Boolean emoji;

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

    public Boolean isEmoji() {
        return emoji;
    }

    public void setEmoji(final Boolean emoji) {
        this.emoji = emoji;
    }
}
