package com.source.project.repos;

import com.source.project.domain.Attribute;
import com.source.project.domain.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AttributeRep extends
        CrudRepository<Attribute, Long>,
        JpaRepository<Attribute, Long>
{
    //List<Attribute> findAllByValues(List<Value> values);
    Attribute findByLabel(String label);
    void removeById(Integer id);
    void removeByLabel(String label);
    //List<Atribute> findAllBy
}
