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
    private ObjEntityRep objEntityRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private UserRep userRep;

    @Override
    public void save(String userId, Integer parentId, String message) {
         if(!message.isEmpty()) {
             Type messageType = typeRep.findById(3);
             Attribute attMess = attributeRep.findById(3);
             Attribute attUser = attributeRep.findById(1);

             ObjEntity obj = new ObjEntity(parentId, messageType);
             Value valUser = new Value(obj, attUser, userId);
             Value valMess = new Value(obj, attMess, message);

             objEntityRep.save(obj);
             valueRep.save(valUser);
             valueRep.save(valMess);
         }
    }

    @Override
    public void delete(Integer objId) {
        Attribute usrId = attributeRep.findById(1);
        Attribute review = attributeRep.findById(3);

        ObjEntity obj = objEntityRep.findById(objId);
        Integer userId = valueRep.findByAttributesAndObjEntity(usrId, obj).getId();
        Integer revId = valueRep.findByAttributesAndObjEntity(review, obj).getId();

        valueRep.removeById(userId);
        valueRep.removeById(revId);
        objEntityRep.removeById(objId);
    }

    @Override
    public List<FilmMessages> getListMessages(Integer parentId) {
        Type mesType = typeRep.findById(3);
        Attribute review = attributeRep.findById(3);
        Attribute usrId = attributeRep.findById(1);

        List<FilmMessages> filmMessagesList = new ArrayList<>();
        List<ObjEntity> objects = objEntityRep.findByTypeAndParentId(mesType, parentId);

        for (ObjEntity obj: objects
             ) {
            Integer objectId = obj.getId();
            String username = valueRep.findByAttributesAndObjEntity(usrId, obj).getValue();
            username = userRep.findById(Integer.valueOf(username)).getUsername();
            String message = valueRep.findByAttributesAndObjEntity(review, obj).getValue();
            FilmMessages filmMes = new FilmMessages(objectId, username, message);
            filmMessagesList.add(filmMes);
        }

        return filmMessagesList;
    }
}
