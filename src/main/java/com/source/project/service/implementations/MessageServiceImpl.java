package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.repos.*;
import com.source.project.service.Constants;
import com.source.project.service.MessageService;
import com.source.project.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private ObjEntityRep objEntityRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private ValueService valueService;

    @Override
    public void create(Integer parentId, List<String> label, List<String> value) {
        try {
            Type messageType = typeRep.findById(Constants.MESSAGE_TYPE_ID);
            ObjEntity object = new ObjEntity(parentId, messageType);
            objEntityRep.save(object);

            for (int i = 0; i < label.size(); i++) {
                if (label.get(i).equals(attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE).getLabel())) {
                    valueService.save(new Value(object,
                            attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE),
                            String.valueOf(object.getId())));
                } else
                    valueService.save(new Value(object,
                            attributeRep.findByLabel(label.get(i)),
                            value.get(i)));
            }
        } catch (NullPointerException e) {
            System.out.println("Message create exception : " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer messageId) {
        objEntityRep.removeById(messageId);
    }

    @Override
    public List<Map<String, String>> getListMessages(Integer parentId) {
        List<Map<String, String>> reviewList = new ArrayList<Map<String, String>>();
        try {
            Type messageType = typeRep.findById(Constants.MESSAGE_TYPE_ID);
            List<ObjEntity> reviews = objEntityRep.findByTypeAndParentId(messageType, parentId);

            List<Attribute> attributesList = new ArrayList<Attribute>();
            List<TypeAttribute> typeAttributes = typeAttributeRep.findByType(messageType);
            for (TypeAttribute ta : typeAttributes
            ) {
                attributesList.add(ta.getAttribute());
            }


            for (ObjEntity object : reviews
            ) {
                Map<String, String> tmpMap = new HashMap<>();
                for (Attribute attribute : attributesList
                ) {
                    if (valueRep.findByAttributesAndObjEntity(attribute, object) == null)
                        tmpMap.put(attribute.getLabel(), " ");
                    else
                        tmpMap.put(attribute.getLabel(),
                                valueRep.findByAttributesAndObjEntity(attribute, object).getValue());
                }
                reviewList.add(tmpMap);
            }
        } catch (NullPointerException e) {
            System.out.println("Message getListMessage exception : " + e.getMessage());
        }
        return reviewList;
    }

    @Override
    public void edit(Integer messageId, List<String> label, List<String> value) {
        try {
            Type messageType = typeRep.findById(Constants.MESSAGE_TYPE_ID);
            Attribute refToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE), messageType).getAttribute();
            ObjEntity message = objEntityRep.findById(messageId);

            for(int i = 0; i < label.size(); i++) {
                Value val = valueRep.findByAttributesAndObjEntity(
                        attributeRep.findByLabel(label.get(i)),
                        message);
                val.setValue(value.get(i));
                valueService.save(val);
            }

        } catch (NullPointerException e) {
            System.out.println("Message editMessage exception : " + e.getMessage());
        }
    }
}
