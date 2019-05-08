package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Actions block request model.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActionsBlock {

    private String type;

    private List<Element> elements;

    @JsonProperty("block_id")
    private String blockId;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(final List<Element> elements) {
        this.elements = elements;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(final String blockId) {
        this.blockId = blockId;
    }
}
