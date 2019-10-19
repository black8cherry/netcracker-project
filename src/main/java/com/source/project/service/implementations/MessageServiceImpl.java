package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.resources.FilmMessages;
import com.source.project.repos.*;
import com.source.project.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private ObjEntityRep objEntityRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private UserRep userRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;

    /*@Override
    public void init() {
        if(typeRep.findByType("message")==null) {
            Type initMes = new Type("message");

            Attribute initUsr = attributeRep.findByLabel("userId"); // here
            Attribute initRev = new Attribute("review");

            TypeAttribute initUsrT = new TypeAttribute(initMes, initUsr);
            TypeAttribute initRevT = new TypeAttribute(initMes, initRev);

            typeRep.save(initMes);
            attributeRep.save(initUsr);
            attributeRep.save(initRev);
            typeAttributeRep.save(initRevT);
            typeAttributeRep.save(initUsrT);
        }
    }*/

    @Override
    public void save(String userId, Integer parentId, String message) {
         if(!message.isEmpty()) {
             Type mesType = typeRep.findByTypename("message");
             Attribute attMess = attributeRep.findByLabel("review");
             Attribute attUser = attributeRep.findByLabel("userId");

             ObjEntity obj = new ObjEntity(parentId, mesType);
             Value valUser = new Value(obj, attUser, userId);
             Value valMess = new Value(obj, attMess, message);

             objEntityRep.save(obj);
             valueRep.save(valUser);
             valueRep.save(valMess);
         }
    }

    @Override
    public void delete(Integer objId) {
        Type mesType = typeRep.findByTypename("message");
        Attribute usrId = attributeRep.findByLabel("userId");
        Attribute review = attributeRep.findByLabel("review");

        ObjEntity obj = objEntityRep.findById(objId);
        Integer userId = valueRep.findByAttributesAndObjEntity(usrId, obj).getId();
        Integer revId = valueRep.findByAttributesAndObjEntity(review, obj).getId();

        valueRep.removeById(userId);
        valueRep.removeById(revId);
        objEntityRep.removeById(objId);
    }

    @Override
    public List<FilmMessages> getListMes(Integer parentId) {
        Type mesType = typeRep.findByTypename("message");
        Attribute usrId = attributeRep.findByLabel("userId");
        Attribute review = attributeRep.findByLabel("review");

        List<FilmMessages> filmMessagesList = new ArrayList<>();
        List<ObjEntity> objects = objEntityRep.findByTypeAndParentId(mesType, parentId);

        for (ObjEntity obj: objects
             ) {
            Integer objectId = obj.getId();
            String username = valueRep.findByAttributesAndObjEntity(usrId, obj).getValue();
            String message = valueRep.findByAttributesAndObjEntity(review, obj).getValue();
            FilmMessages filmMes = new FilmMessages(objectId, username, message);
            filmMessagesList.add(filmMes);
        }

        return filmMessagesList;
    }
}
