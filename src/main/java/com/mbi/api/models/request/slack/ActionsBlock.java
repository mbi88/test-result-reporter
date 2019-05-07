package com.mbi.api.models.request.slack;

import java.util.List;

/**
 * Actions block request model.
 */
public class ActionsBlock extends Block {

    private String type;

    private List<Element> elements;

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
}
