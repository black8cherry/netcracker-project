package com.source.project.service.implementations;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.repos.ObjEntityRep;
import com.source.project.service.ObjEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjEntityServiceImpl implements ObjEntityService {

    @Autowired
    private ObjEntityRep objEntityRep;

    @Override
    public List<ObjEntity> findByNameOrderByName(String name) {
        return objEntityRep.findByNameOrderByName(name);
    }

    @Override
    public ObjEntity findById(Integer id) {
        return objEntityRep.findById(id);
    }

    @Override
    public void removeById(Integer id) {
        objEntityRep.removeById(id);
    }

    @Override
    public List<ObjEntity> findAll() {
        return objEntityRep.findAll();
    }

    @Override
    public void save(ObjEntity movie) {
        objEntityRep.save(movie);
    }

    @Override
    public List<ObjEntity> findAll(Sort sort) {
        return objEntityRep.findAll(sort);
    }

    @Override
    public void removeByNameAndParentIdAndType(String uid, Integer id, Type type) {
        objEntityRep.removeByNameAndParentIdAndType(uid, id, type);
    }

    @Override
    public Float countByTypeAndParentId(Type type, Integer id) {
        return objEntityRep.countByTypeAndParentId(type, id);
    }

    @Override
    public ObjEntity findByNameAndParentIdAndType(String uid, Integer id, Type type) {
        return objEntityRep.findByNameAndParentIdAndType(uid, id, type);
    }

    @Override
    public List<ObjEntity> findByTypeAndParentId(Type type, Integer id) {
        return objEntityRep.findByTypeAndParentId(type, id);
    }
}
