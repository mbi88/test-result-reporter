package com.mbi.api.models.request.slack;

import java.util.List;

/**
 * Attachment request model.
 */
public class Attachment {

    private String fallback;

    private String color;

    private String authorName;

    private List<Field> fields;

    private String footer;

    private String footerIcon;

    private int ts;

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

    public int getTs() {
        return ts;
    }

    public void setTs(final int ts) {
        this.ts = ts;
    }
}
