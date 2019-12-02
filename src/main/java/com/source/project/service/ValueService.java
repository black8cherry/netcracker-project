package com.source.project.service;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Value;

import java.util.List;

public interface ValueService {
    void save(Value value);
    void findById(Integer id);
    List<Value> getMainImages(List<ObjEntity> movies);
    Value getMainImage(ObjEntity movies);
    void remove(Value t);
}
