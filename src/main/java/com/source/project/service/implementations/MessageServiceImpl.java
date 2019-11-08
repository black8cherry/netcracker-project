package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.domain.resources.MessageConnector;
import com.source.project.repos.*;
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

    /* Attributes
     * userId     1
     * refToObj   2
     * review     3
     * rate       4
     *  Types
     * video        1
     * favorite     2
     * message      3
     * rating       4
     */

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
    private UserRep userRep;
    @Autowired
    private ValueService valueService;

    @Override
    public void create(Integer parentId, List<String> label, List<String> value) {

        Type messageType = typeRep.findById(3);
        ObjEntity object = new ObjEntity(parentId, messageType);
        objEntityRep.save(object);

        for(int i = 0; i < label.size(); i++) {
            if(label.get(i).equals(attributeRep.findById(2).getLabel())) {
                valueService.save(new Value(object,
                        attributeRep.findById(2),
                        String.valueOf(object.getId())));
                System.out.println("it worked");
            }
            else
                valueService.save(new Value(object,
                    attributeRep.findByLabel(label.get(i)),
                    value.get(i)));
        }

    }

    @Override
    public void delete(Integer objId) {
        objEntityRep.removeById(objId);
    }

    @Override
    public List<MessageConnector> getListMessages(Integer parentId) {

        Type messageType = typeRep.findById(3);
        List<ObjEntity> reviews = objEntityRep.findByTypeAndParentId(messageType, parentId);
        
        List<Attribute> attributesList = new ArrayList<Attribute>();
        List<TypeAttribute> typeAttributes = typeAttributeRep.findByType(messageType);
        for (TypeAttribute ta: typeAttributes
             ) {
            attributesList.add(ta.getAttribute());
        }
        
        List<MessageConnector> reviewList = new ArrayList<MessageConnector>();

        for (ObjEntity object: reviews
             ) {
            Map<String, String> tmpMap = new HashMap<>();
            for (Attribute attribute: attributesList
                 ) {
                if(valueRep.findByAttributesAndObjEntity(attribute, object)==null)
                    tmpMap.put(attribute.getLabel()," ");
                else
                    tmpMap.put(attribute.getLabel(),
                        valueRep.findByAttributesAndObjEntity(attribute, object).getValue());
            }
            reviewList.add(new MessageConnector(tmpMap));
        }
        
        return reviewList;
    }
}
