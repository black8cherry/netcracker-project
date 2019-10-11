package com.source.project.service;

import com.source.project.domain.Attribute;
import com.source.project.domain.Objects;
import com.source.project.domain.Value;

import java.util.List;

public interface ValueService {
    List<Value> findAllByObjects(Objects object);
    Value findByAttributesAndObjects(Attribute attribute, Objects objects);
    void save(Value value);
}
