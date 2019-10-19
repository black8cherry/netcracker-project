package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
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
    public List<Value> findAllByObjects(ObjEntity movie) {
        return valueRep.findAllByObjEntity(movie);
    }

    @Override
    public Value findByAttributesAndObjects(Attribute attribute, ObjEntity movie) {
        return valueRep.findByAttributesAndObjEntity(attribute, movie);
    }

    @Override
    public Value findByAttributesAndValue(Attribute attribute, String value) {
        return valueRep.findByAttributesAndValue(attribute, value);
    }

    @Override
    public void save(Value value) {
        valueRep.save(value);
    }

    @Override
    public void removeById(Integer id) {
        valueRep.removeById(id);
    }

    @Override
    public void findById(Integer id) {
        valueRep.removeById(id);
    }

    @Override
    public void removeByObjects(ObjEntity movie) {
        valueRep.findByObjEntity(movie);
    }

    @Override
    public List<Value> findByObjectsAndAttributes(ObjEntity movie, Attribute attribute) {
        return valueRep.findByObjEntityAndAttributes(movie, attribute);
    }
}
