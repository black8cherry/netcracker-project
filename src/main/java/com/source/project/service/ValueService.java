package com.source.project.service;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Value;

import java.util.Collection;
import java.util.List;

public interface ValueService {
    void save(Value value);
    void findById(Integer id);
    List<Value> getValuesByObjEntityInAndAttributes(Collection<ObjEntity> objEntity, Attribute attributes);
    Value findByAttributesAndObjEntity(Attribute attribute, ObjEntity movie);
}
