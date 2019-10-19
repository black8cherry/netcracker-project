package com.source.project.service;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ObjEntityService {
    ObjEntity findById(Integer id);
    ObjEntity findByNameAndParentIdAndType(String uid, Integer id, Type type);
    List<ObjEntity> findAll();
    List<ObjEntity> findByNameOrderByName(String name);
    List<ObjEntity> findAll(Sort sort);
    List<ObjEntity> findByTypeAndParentId(Type type, Integer id);
    void save(ObjEntity objEntity);
    void removeById(Integer id);
    void removeByNameAndParentIdAndType(String uid, Integer id, Type type);
    Float countByTypeAndParentId(Type type, Integer id);

}
