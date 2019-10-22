package com.source.project.service;

import com.source.project.domain.Type;

import java.util.Collection;
import java.util.List;

public interface TypeService {
    Type findById(Integer id);
    Type findByTypename(String typename);
    Iterable<Type> findAll();
    Collection<Type> findByParentId(Integer id);
    void save(Integer parentId, String typename);
    void save(String typename);
    List<Type> findByParentIdIsNull();
    void delete(Type typename);
}
