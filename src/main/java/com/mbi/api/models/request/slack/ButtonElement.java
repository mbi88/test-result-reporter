package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ButtonElement request model.
 */
public class ButtonElement extends Element {

    private String type;

    private Text text;

    @JsonProperty("action_id")
    private String actionId;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Text getText() {
        return text;
    }

    public void setText(final Text text) {
        this.text = text;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(final String actionId) {
        this.actionId = actionId;
    }
}
