package com.source.project.repos;

import com.source.project.domain.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeRep extends CrudRepository<Type, Long> {
    Type findById(Integer id);
}
