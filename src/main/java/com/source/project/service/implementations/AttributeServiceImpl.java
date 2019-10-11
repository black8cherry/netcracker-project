package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.repos.AttributeRep;
import com.source.project.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRep attributeRep;

    @Override
    public Attribute findByLabel(String label) {
        return attributeRep.findByLabel(label);
    }

    @Override
    public void removeByLabel(String label) {
        attributeRep.removeByLabel(label);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRep.findAll();
    }

    @Override
    public void save(Attribute attribute) {
        attributeRep.save(attribute);
    }
}
