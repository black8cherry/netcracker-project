package com.source.project.service;

import com.source.project.domain.Type;

import java.util.List;

public interface TypeService {
    Type findById(Integer id);
    Type findByType(String type);
    Iterable<Type> findAll();
    void save(Type type);
    void delete(Type type);
}
