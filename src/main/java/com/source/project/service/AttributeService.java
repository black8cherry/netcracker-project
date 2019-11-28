package com.source.project.service;

import com.source.project.domain.Attribute;
import com.source.project.domain.Type;

import java.util.List;
import java.util.Map;

public interface AttributeService {
    Attribute findByLabel(String label);
    Attribute findById(Integer id);
    List<Attribute> getListForRefactorAttributeValues(Map<String, String> tmpMap);
    List<Attribute> findByObjectEntityType(Type type);
    List<Attribute> findAll();
    List<Attribute> attributesNotInObj(Integer typeId);
    List<Attribute> getParentAtt(Integer typeId);
    void save(String label, String labelType);
    void edit(Integer attributeId, String label, String labelType);
    void removeByLabel(String label);
    Attribute findByLabelType(String type);
}
