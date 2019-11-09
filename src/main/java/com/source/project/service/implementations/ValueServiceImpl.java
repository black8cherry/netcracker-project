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
    @Autowired
    private AttributeRep attributeRep;

    @Override
    public void save(Value value) {
        /*String labelType = value.getAttributes().getLabelType();
        String stringVal = value.getValue();
        if(labelType.equals("char")) {
                valueRep.save(value);
        } else if(labelType.equals("numerical")) {
            if (!stringVal.matches("[A-Za-z]+")) {
                valueRep.save(value);
            } else {
                value.setValue("invalid attribute value");
                valueRep.save(value);
            }
        } else {
            value.setValue("invalid attribute type");*/
            valueRep.save(value);
    }

    @Override
    public void findById(Integer id) {
        valueRep.removeById(id);
    }
}
