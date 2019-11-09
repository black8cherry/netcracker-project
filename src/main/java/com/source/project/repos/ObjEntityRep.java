package com.source.project.repos;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ObjEntityRep extends
        CrudRepository<ObjEntity, Long>,
        JpaRepository<ObjEntity, Long>
{
    Float countByTypeAndParentId(Type type, Integer id);
    List<ObjEntity> findByTypeAndParentId(Type type, Integer id);
    List<ObjEntity> findByType(Type type);
    List<ObjEntity> getObjEntitiesByTypeInOrderByName(Collection<Type> type);
    List<ObjEntity> findByNameIsContaining(String filter);
    ObjEntity findById(Integer id);
    ObjEntity findByParentIdAndType(Integer id, Type type);
    void removeById(Integer id);
}
