package com.source.project.service;

import com.source.project.domain.Attribute;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;

import java.util.List;

public interface TypeAttributeService {
    List<TypeAttribute> findByType(Type type);
    void removeByAttributeAndType(Attribute attribute, Type type);
    void save(String label, Integer typeId);
}
