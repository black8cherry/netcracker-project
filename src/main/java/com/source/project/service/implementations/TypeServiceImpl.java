package com.source.project.service.implementations;

import com.source.project.domain.Type;
import com.source.project.repos.TypeRep;
import com.source.project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRep typeRep;

    @Override
    public Type findById(Integer id) {
        return typeRep.findById(id);
    }

    @Override
    public Type findByType(String type) {
        return typeRep.findByType(type);
    }

    @Override
    public Iterable<Type> findAll() {
        return typeRep.findAll();
    }

    @Override
    public void save(Type type) {
        typeRep.save(type);
    }

    @Override
    public void delete(Type type) {
        typeRep.delete(type);
    }
}
