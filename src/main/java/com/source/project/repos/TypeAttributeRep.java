package com.source.project.repos;

import com.source.project.domain.Attribute;
import com.source.project.domain.Objects;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.w3c.dom.Attr;

import java.util.List;

public interface TypeAttributeRep extends
        CrudRepository<TypeAttribute, Long>,
        JpaRepository<TypeAttribute, Long>
{
    List<TypeAttribute> findByType(Type type);
    List<TypeAttribute> findByTypeOrderByAttribute(Type type);
    TypeAttribute findByAttributeAndType(Attribute attribute, Type type);
    void removeById(Integer id);
    void removeByAttribute(Attribute attribute);
}
