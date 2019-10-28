package com.source.project.service.implementations;

import com.source.project.domain.Type;
import com.source.project.repos.TypeRep;
import com.source.project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if(typeRep.findByParentIdAndTypename(parentId, typename)==null) {
            Type type = new Type(parentId, typename);
            typeRep.save(type);
        }
    }

    @Override
    public List<Type> findByParentIdIsNotNull() {
        return typeRep.findByParentIdIsNotNull();
    }

    @Override
    public List<Type> findByParentIdIsNull() {
        return typeRep.findByParentIdIsNull();
    }

    @Override
    public void delete(Integer id) {
        List<Type> typeList = typeRep.findTreeFromParent(id);
        for (Type type: typeList
             ) {
            typeRep.delete(type);
        }
    }

    @Override
    public List<Type> findTreeFromParent(Integer parentId) {
        return typeRep.findTreeFromParent(parentId);
    }
}
