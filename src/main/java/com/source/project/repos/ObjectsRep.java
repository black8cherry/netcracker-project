package com.source.project.repos;

import com.source.project.domain.Objects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectsRep extends
        CrudRepository<Objects, Long>,
        JpaRepository<Objects, Long>
{

    List<Objects> findByNameOrderByName(String name);
    Objects findById(Integer id);
    void removeById(Integer id);
}
