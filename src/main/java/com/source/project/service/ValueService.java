package com.source.project.service;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Value;

import java.util.List;

public interface ValueService {
    List<Value> findByObjectsAndAttributes(ObjEntity movie, Attribute attribute);
    List<Value> findAllByObjects(ObjEntity movie);
    Value findByAttributesAndObjects(Attribute attribute, ObjEntity movie);
    Value findByAttributesAndValue(Attribute attribute, String value);
    void save(Value value);
    void removeById(Integer id);
    void removeByObjects(ObjEntity movie);
    void findById(Integer id);
}
