package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.TypeRep;
import com.source.project.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private TypeRep typeRep;

    @Override
    public List<Attribute> attributesNotInObj(Integer typeId) {
        List<Attribute> attributesAll = attributeRep.findAll();
        List<Attribute> attributesNotInObj = new ArrayList<Attribute>();

        try {
            for (Attribute att : attributesAll
            ) {
                if (typeAttributeRep.findByAttributeAndType(att, typeRep.findById(typeId)) == null) {

                    attributesNotInObj.add(att);
                }
            }
        } catch (NullPointerException e) {}

        return attributesNotInObj;
    }

    @Override
    public List<Attribute> getParentAtt(Integer typeId) {
        List<Attribute> attributeList = new ArrayList<Attribute>();

        List<Type> typeList = typeRep.findTreeFromChild(typeId);
        List<TypeAttribute> typeAttributeList;

        for (Type type: typeList
             ) {
            typeAttributeList = typeAttributeRep.findByTypeOrderByAttribute(type);
            for (TypeAttribute tp: typeAttributeList
            ) {
                attributeList.add(tp.getAttribute());
            }
        }

        return attributeList;
    }

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
        if(attributeRep.findByLabel(attribute.getLabel())==null) {
            attributeRep.save(attribute);
        }
    }
}
