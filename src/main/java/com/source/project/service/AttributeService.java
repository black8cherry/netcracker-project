package com.source.project.service;

import com.source.project.domain.Attribute;

import java.util.List;

public interface AttributeService {
    Attribute findByLabel(String label);
    Attribute findById(Integer id);
    void removeByLabel(String label);
    List<Attribute> findAll();
    void save(String label, String labelType);
    void edit(Integer attributeId, String label, String labelType);
    List<Attribute> attributesNotInObj(Integer typeId);
    List<Attribute> getParentAtt(Integer typeId);
}
