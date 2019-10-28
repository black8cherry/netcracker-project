package com.source.project.repos;

import com.source.project.domain.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttributeRep extends
        CrudRepository<Attribute, Long>,
        JpaRepository<Attribute, Long>
{
    Attribute findByLabel(String label);
    void removeByLabel(String label);
    Attribute findById(Integer id);
}
