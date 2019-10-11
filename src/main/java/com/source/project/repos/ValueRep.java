package com.source.project.repos;

import com.source.project.domain.Attribute;
import com.source.project.domain.Objects;
import com.source.project.domain.Value;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValueRep extends CrudRepository<Value, Long> {
    List<Value> findAllByObjects(Objects object);
    Value findByAttributesAndObjects(Attribute attribute, Objects objects);
}
