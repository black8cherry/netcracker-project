package com.source.project.repos;

import com.source.project.domain.Attribute;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeAttributeRep extends
        CrudRepository<TypeAttribute, Long>,
        JpaRepository<TypeAttribute, Long>
{
    List<TypeAttribute> findAllByAttribute(Attribute attribute);
    List<TypeAttribute> findByType(Type type);
    List<TypeAttribute> findByTypeOrderByAttribute(Type type);
    TypeAttribute findByAttributeAndType(Attribute attribute, Type type);
    void removeByAttributeAndType(Attribute attribute, Type type);

}
