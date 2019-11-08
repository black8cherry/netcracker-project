package com.source.project.domain.resources;

import java.util.Map;

public class MessageConnector {
    private Map<String, String> attributeValueMap;

    MessageConnector(){}

    public MessageConnector(Map<String, String> attributeValueMap) {
        this.attributeValueMap = attributeValueMap;
    }

    public Map<String, String> getAttributeValueMap() {
        return attributeValueMap;
    }

    public void setAttributeValueMap(Map<String, String> attributeValueMap) {
        this.attributeValueMap = attributeValueMap;
    }
}
