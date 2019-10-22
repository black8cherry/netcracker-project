package com.source.project.repos;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ValueRep extends CrudRepository<Value, Long> {
    Value findByAttributesAndObjEntity(Attribute attribute, ObjEntity movie);
    Value findByObjEntity(ObjEntity movie);
    Value findById(Integer id);
    Value findByObjEntityAndAttributesAndValue(ObjEntity movie, Attribute attribute, String value);
    Optional<Value> getValueByObjEntityInAndValueAndAttributes(Collection<ObjEntity> movies, String value, Attribute attributes);
    Value findByAttributesAndValue(Attribute attribute, String value);
    List<Value> findAllByObjEntity(ObjEntity movie);
    List<Value> findByObjEntityAndAttributes(ObjEntity movie, Attribute attribute);
    void removeById(Integer id);
    void removeByObjEntityAndAttributesAndValue(ObjEntity movie, Attribute attribute, String value);
}
