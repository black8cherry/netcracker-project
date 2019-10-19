package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.domain.ObjEntity;
import com.source.project.repos.*;
import com.source.project.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private ObjEntityRep objEntityRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;

    /*@Override
    public void init() {
        if (typeRep.findByType("favoriteList") == null) {
            Type initFav = new Type("favoriteList");

            Attribute initUsr = new Attribute("userId");
            Attribute initRef = new Attribute("refToObject");

            TypeAttribute initUsrT = new TypeAttribute(initFav, initUsr);
            TypeAttribute initRefT = new TypeAttribute(initFav, initRef);

            typeRep.save(initFav);
            attributeRep.save(initUsr);
            attributeRep.save(initRef);
            typeAttributeRep.save(initRefT);
            typeAttributeRep.save(initUsrT);
            }
    }*/

    @Override
    public void save(String userId, String objectId) {

        Type favType = typeRep.findByTypename("favoriteList");
        Attribute attUserId = attributeRep.findByLabel("userId");
        Attribute attRefToObj = attributeRep.findByLabel("refToObject");
        Collection<ObjEntity> movies = objEntityRep.findByType(favType);

        if (!valueRep.getValueByObjEntityInAndValueAndAttributes(movies, userId, attUserId).isPresent()) {
            ObjEntity savingObjEntity = new ObjEntity(favType);
            ObjEntity obj = objEntityRep.save(savingObjEntity);

            Value savingValueUser = new Value(obj, attUserId, userId);
            valueRep.save(savingValueUser);
        }
        ObjEntity objOfUser = valueRep.getValueByObjEntityInAndValueAndAttributes(movies, userId, attUserId).get().getObjEntity();
        Value savingValueRef = new Value(objOfUser, attRefToObj, objectId);
        valueRep.save(savingValueRef);
    }

    @Override
    public void delete(String userId, String objectId) {

        Type favType = typeRep.findByTypename("favoriteList");
        Attribute attUserId = attributeRep.findByLabel("userId");
        Attribute attRefToObj = attributeRep.findByLabel("refToObject");
        Collection<ObjEntity> movies = objEntityRep.findByType(favType);

        ObjEntity objOfUser = valueRep.getValueByObjEntityInAndValueAndAttributes(movies, userId, attUserId).get().getObjEntity();
        valueRep.removeByObjEntityAndAttributesAndValue(objOfUser, attRefToObj, objectId);
    }

    @Override
    public boolean check(String userId, String movieId) {
        boolean checkObjEntity = false;

        Type favType = typeRep.findByTypename("favoriteList");
        Attribute attUserId = attributeRep.findByLabel("userId");
        Attribute attRefToObj = attributeRep.findByLabel("refToObject");
        Collection<ObjEntity> movies = objEntityRep.findByType(favType);

        if (valueRep.getValueByObjEntityInAndValueAndAttributes(movies, userId, attUserId).isPresent()) {
            ObjEntity objOfUser = valueRep.getValueByObjEntityInAndValueAndAttributes(movies, userId, attUserId).get().getObjEntity();
            if (valueRep.findByObjEntityAndAttributesAndValue(objOfUser, attRefToObj, movieId) != null)
                checkObjEntity = true;
        }
        return checkObjEntity;
    }

    @Override
    public void create(String userId) {
        Type favType = typeRep.findByTypename("favoriteList");
        Attribute attUserId = attributeRep.findByLabel("userId");

        ObjEntity savingObjEntity = new ObjEntity(favType);
        ObjEntity obj = objEntityRep.save(savingObjEntity);

        Value savingValueUser = new Value(obj, attUserId, userId);
        valueRep.save(savingValueUser);
    }
}
