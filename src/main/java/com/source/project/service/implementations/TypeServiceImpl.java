package com.source.project.service.implementations;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;
import com.source.project.repos.ObjEntityRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.TypeRep;
import com.source.project.service.ObjEntityService;
import com.source.project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRep typeRep;
    @Autowired
    private ObjEntityRep objEntityRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private ObjEntityService objEntityService;

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
        for (Type type: typeRep.findTreeFromParent(id)
             ) {
            for (ObjEntity obj : objEntityRep.findByType(type)
            ) {
                objEntityService.removeById(obj.getId());
            }
            for (TypeAttribute tp: typeAttributeRep.findByType(type)
                 ) {
                typeAttributeRep.delete(tp);
            }
            typeRep.delete(type);
        }
    }

    @Override
    public List<Type> findTreeFromParent(Integer parentId) {
        return typeRep.findTreeFromParent(parentId);
    }
}
