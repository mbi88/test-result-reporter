package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Attachment request model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

    private String title;

    @JsonProperty("callback_id")
    private String callbackId;

    @JsonProperty("short")
    private boolean shortField;

    @JsonProperty("attachment_type")
    private String attachmentType;

    private List<Action> actions;

    private String text;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(final String callbackId) {
        this.callbackId = callbackId;
    }

    public boolean isShortField() {
        return shortField;
    }

    public void setShortField(final boolean shortField) {
        this.shortField = shortField;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(final String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(final List<Action> actions) {
        this.actions = actions;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
