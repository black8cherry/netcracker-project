package com.source.project.service;

import com.source.project.domain.Attribute;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TypeAttributeService {
    List<TypeAttribute> findByType(Type type);
    List<TypeAttribute> findByTypeOrderByAttribute(Type type);
    TypeAttribute findByAttributeAndType(Attribute attribute, Type type);
    void removeByAttribute(Attribute attribute);
    void removeByAttributeAndType(Attribute attribute, Type type);
    List<TypeAttribute> findAll(Sort sort);
    void save(TypeAttribute typeAttribute);
}
