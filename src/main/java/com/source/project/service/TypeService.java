package com.source.project.service;

import com.source.project.domain.Type;

import java.util.List;

public interface TypeService {
    Type findById(Integer id);
    Type findByType(String typename);
    Iterable<Type> findAll();
    void save(Type typename);
    void delete(Type typename);
}
