package com.source.project.service;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Value;

import java.util.List;

public interface ValueService {
    void save(Value value);
    void findById(Integer id);
}
