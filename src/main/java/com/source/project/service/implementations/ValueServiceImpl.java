package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.Objects;
import com.source.project.domain.Value;
import com.source.project.repos.ValueRep;
import com.source.project.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValueServiceImpl implements ValueService {

    @Autowired
    private ValueRep valueRep;

    @Override
    public List<Value> findAllByObjects(Objects object) {
        return valueRep.findAllByObjects(object);
    }

    @Override
    public Value findByAttributesAndObjects(Attribute attribute, Objects objects) {
        return valueRep.findByAttributesAndObjects(attribute, objects);
    }

    @Override
    public void save(Value value) {
        valueRep.save(value);
    }
}
