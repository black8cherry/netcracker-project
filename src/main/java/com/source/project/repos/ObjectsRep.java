package com.source.project.repos;

import com.source.project.domain.Objects;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ObjectsRep extends CrudRepository<Objects, Long> {
    List<Objects> findByName(String name);
    Objects findById(Integer id);
    void deleteById(Integer id);
    void removeById(Integer id);
}
