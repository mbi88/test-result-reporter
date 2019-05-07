package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Accessory request model.
 */
public class ImageAccessory extends Accessory {

    private String type;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("alt_text")
    private String altText;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(final String altText) {
        this.altText = altText;
    }
}
