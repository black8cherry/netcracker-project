package com.source.project.repos;

import com.source.project.domain.Atribute;
import com.source.project.domain.Value;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AtributeRep extends CrudRepository<Atribute, Long> {
    List<Atribute> findAllByValues(List<Value> values);
    //List<Atribute> findAllBy
}
