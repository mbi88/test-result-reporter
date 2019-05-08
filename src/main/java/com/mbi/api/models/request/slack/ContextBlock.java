package com.mbi.api.models.request.slack;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Context block request model.
 */
public class ContextBlock extends Block {

    private String type;

    private List<TextElement> elements;

    @JsonProperty("block_id")
    private String blockId;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<TextElement> getElements() {
        return elements;
    }

    public void setElements(final List<TextElement> elements) {
        this.elements = elements;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(final String blockId) {
        this.blockId = blockId;
    }
}
