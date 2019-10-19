package com.source.project.repos;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjEntityRep extends
        CrudRepository<ObjEntity, Long>,
        JpaRepository<ObjEntity, Long>
{
    Float countByTypeAndParentId(Type type, Integer id);
    List<ObjEntity> findByTypeAndParentId(Type type, Integer id);
    List<ObjEntity> findByNameOrderByName(String name);
    List<ObjEntity> findByType(Type type);
    ObjEntity findById(Integer id);
    ObjEntity findByIdAndType(Integer id, Type type);
    ObjEntity findByNameAndParentIdAndType(String uid, Integer id, Type type);
    ObjEntity findByParentIdAndType(Integer id, Type type);
    void removeById(Integer id);
    void removeByNameAndParentIdAndType(String uid, Integer id, Type type);
}
