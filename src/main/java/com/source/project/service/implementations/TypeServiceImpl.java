package com.source.project.service.implementations;

import com.source.project.domain.Type;
import com.source.project.repos.TypeRep;
import com.source.project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRep typeRep;

    @Override
    public Type findById(Integer id) {
        return typeRep.findById(id);
    }

    @Override
    public Type findByTypename(String typename) {
        return typeRep.findByTypename(typename);
    }

    @Override
    public Iterable<Type> findAll() {
        return typeRep.findAll();
    }

    @Override
    public void save(String typename) {
        Type type = new Type(typename);
        typeRep.save(type);
    }

    @Override
    public void save(Integer parentId, String typename) {
        Type type = new Type(parentId, typename);
        typeRep.save(type);
    }

    @Override
    public List<Type> findByParentIdIsNull() {
        return typeRep.findByParentIdIsNull();
    }

    @Override
    public void delete(Type type) {
        typeRep.delete(type);
    }

    @Override
    public Collection<Type> findByParentId(Integer id) {
        return typeRep.findByParentId(id);
    }
}
