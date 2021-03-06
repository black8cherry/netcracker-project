package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;
import com.source.project.domain.Value;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.TypeRep;
import com.source.project.repos.ValueRep;
import com.source.project.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private ValueRep valueRep;

    @Override
    public List<Attribute> findByObjectEntityType(Type type) {
        List<Attribute> attributesList = new ArrayList<Attribute>();

        for (Type tp: typeRep.findTreeFromChild(type.getId())
             ) {
            List<TypeAttribute> typeAttributes = typeAttributeRep.findByType(tp);
            for (TypeAttribute ta: typeAttributes
            ) {
                attributesList.add(ta.getAttribute());
            }
        }

        return attributesList;
    }

    @Override
    public List<Attribute> getListForRefactorAttributeValues(Map<String, String> tmpMap) {

        List<Attribute> attributes = new ArrayList<Attribute>();

        for (String key: tmpMap.keySet()
             ) {
            attributes.add(attributeRep.findByLabel(key));
        }

        return attributes;
    }

    @Override
    public List<Attribute> attributesNotInObj(Integer typeId) {
        List<Attribute> attributesAll = attributeRep.findAll();
        List<Attribute> attributesNotInObj = new ArrayList<Attribute>();

        List<Type> typeListParent = typeRep.findTreeFromParent(typeId);
        List<Type> typeListChild = typeRep.findTreeFromChild(typeId);
        List<TypeAttribute> typeAttributeList;

        try {
            for (Attribute att : attributesAll
            ) {
                if (typeAttributeRep.findByAttributeAndType(att, typeRep.findById(typeId)) == null) {

                    attributesNotInObj.add(att);
                }
            }
        } catch (NullPointerException e) {}

        for (Type type: typeListChild
        ) {
            typeAttributeList = typeAttributeRep.findByTypeOrderByAttribute(type);
            for (TypeAttribute tp: typeAttributeList
            ) {
                attributesNotInObj.remove(tp.getAttribute());
            }
        }

        for (Type type: typeListParent
        ) {
            typeAttributeList = typeAttributeRep.findByTypeOrderByAttribute(type);
            for (TypeAttribute tp: typeAttributeList
            ) {
                attributesNotInObj.remove(tp.getAttribute());
            }
        }

        return attributesNotInObj;
    }

    @Override
    public Attribute findById(Integer id) {
        return attributeRep.findById(id);
    }

    @Override
    public void edit(Integer attributeId, String label, String labelType) {
        Attribute attribute = attributeRep.findById(attributeId);

        attribute.setLabelType(labelType);

        if (attributeRep.findByLabel(label)==null) {
            attribute.setLabel(label);
        }

        attributeRep.save(attribute);
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
    public void removeById(Integer id) {
        for (TypeAttribute tp: typeAttributeRep.findAllByAttribute(attributeRep.findById(id))
        ) {
            typeAttributeRep.delete(tp);
        }
        for (Value val: valueRep.findAllByAttributes(attributeRep.findById(id))
             ) {
            valueRep.delete(val);
        }
        attributeRep.removeById(id);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRep.findAll();
    }

    @Override
    public void save(String label, String labelType) {
        if(attributeRep.findByLabel(label)==null) {
            Attribute attribute = new Attribute(label, labelType);
            attributeRep.save(attribute);
        }
    }
}
