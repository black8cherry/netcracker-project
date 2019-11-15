package com.source.project.service.implementations;

import com.source.project.domain.Value;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.ValueRep;
import com.source.project.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValueServiceImpl implements ValueService {

    @Autowired
    private ValueRep valueRep;

    @Override
    public void save(Value value) {
            valueRep.save(value);
    }

    @Override
    public void findById(Integer id) {
        valueRep.removeById(id);
    }
}
