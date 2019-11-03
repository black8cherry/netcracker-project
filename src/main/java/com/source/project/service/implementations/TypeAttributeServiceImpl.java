package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.TypeRep;
import com.source.project.service.TypeAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeAttributeServiceImpl implements TypeAttributeService {

    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeRep typeRep;

    @Override
    public List<TypeAttribute> findByType(Type type) {
        return typeAttributeRep.findByType(type);
    }

    @Override
    public TypeAttribute findByAttributeAndType(Attribute attribute, Type type) {
        return typeAttributeRep.findByAttributeAndType(attribute, type);
    }

    @Override
    public void removeByAttributeAndType(Attribute attribute, Type type) {
        typeAttributeRep.removeByAttributeAndType(attribute, type);
    }

    @Override
    public List<TypeAttribute> findAll(Sort sort) {
        return typeAttributeRep.findAll(sort);
    }

    @Override
    public void save(String label, Integer typeId) {

        if (typeAttributeRep.findByAttributeAndType(
                attributeRep.findByLabel(label),
                typeRep.findById(typeId)) == null
        ) {
            typeAttributeRep.save(new TypeAttribute(
                    typeRep.findById(typeId),
                    attributeRep.findByLabel(label)));
        }
    }
}
