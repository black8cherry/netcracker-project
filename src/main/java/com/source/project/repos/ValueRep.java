package com.source.project.repos;

import com.source.project.domain.Attribute;
import com.source.project.domain.Objects;
import com.source.project.domain.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ValueRep extends CrudRepository<Value, Long> {
    List<Value> findAllByObjects(Objects object);
    Value findByAttributes(Attribute attribute);
    Value findByAttributesAndObjects(Attribute attribute, Objects objects);
    //@Query("select a.attribute, v.value from attribute a, value v where")
    List<Value> findAllByObjectsOrderByAttributes(Objects objects);
}
