package com.source.project.service;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Value;

import java.util.List;

public interface ValueService {
    List<Value> findAllByObjects(ObjEntity movie);
    Value findByAttributesAndObjects(Attribute attribute, ObjEntity movie);
    void save(Value value);
    void findById(Integer id);
}
