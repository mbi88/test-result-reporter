package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Attachment request model.
 */
public class Attachment {

    private String fallback;

    private String color;

    @JsonProperty("author_name")
    private String authorName;

    private List<Field> fields;

    private String footer;

    @JsonProperty("footer_icon")
    private String footerIcon;

    private long ts;

    public String getFallback() {
        return fallback;
    }

    public void setFallback(final String fallback) {
        this.fallback = fallback;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(final List<Field> fields) {
        this.fields = fields;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(final String footer) {
        this.footer = footer;
    }

    public String getFooterIcon() {
        return footerIcon;
    }

    public void setFooterIcon(final String footerIcon) {
        this.footerIcon = footerIcon;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(final long ts) {
        this.ts = ts;
    }
}
