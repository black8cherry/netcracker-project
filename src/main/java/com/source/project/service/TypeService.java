package com.source.project.service;

import com.source.project.domain.Type;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface TypeService {
    Type findById(Integer id);
    Iterable<Type> findAll();
    List<Type> findTreeFromParent(Integer parentId);
    void save(Integer parentId, String typename);
    void save(String typename);
    List<Type> findByParentIdIsNull();
    List<Type> findByParentIdIsNotNull();
    void delete(Integer id);
}
