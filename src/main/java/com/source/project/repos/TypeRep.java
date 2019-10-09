package com.source.project.repos;

import com.source.project.domain.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRep extends CrudRepository<Type, Long> {
    Type findById(Integer id);
    Type findByType(String type);
}
