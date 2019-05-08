package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Block request model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SectionBlock {

    private String type;

    private Text text;

    private List<Text> fields;

    private Accessory accessory;

    @JsonProperty("block_id")
    private String blockId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public List<Text> getFields() {
        return fields;
    }

    public void setFields(List<Text> fields) {
        this.fields = fields;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(final String blockId) {
        this.blockId = blockId;
    }
}
