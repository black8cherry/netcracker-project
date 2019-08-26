package com.source.project.repos;

import com.source.project.domain.Objects;
import com.source.project.domain.Value;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ValueRep extends CrudRepository<Value, Long> {
    List<Value> findAllByObjects(Objects object);
}
