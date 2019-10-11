package com.source.project.service;

import com.source.project.domain.Objects;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ObjectsService {
    List<Objects> findByNameOrderByName(String name);
    Objects findById(Integer id);
    void removeById(Integer id);
    List<Objects> findAll();
    List<Objects> findAll(Sort sort);
    void save(Objects objects);
}
