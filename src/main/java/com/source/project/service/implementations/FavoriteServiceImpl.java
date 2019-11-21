package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.Value;
import com.source.project.repos.*;
import com.source.project.service.Constants;
import com.source.project.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    public void save(String userId, String objectId) {
        try {
            Type favType = typeRep.findById(Constants.FAVORITE_TYPE_ID);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.USER_ID_ATTRIBUTE), favType).getAttribute();
            Attribute attRefToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE), favType).getAttribute();
            Collection<ObjEntity> favoriteObjects = objEntityRep.findByType(favType);

            ObjEntity favoriteObject = valueRep.getValueByObjEntityInAndValueAndAttributes(favoriteObjects, userId, attUserId).get().getObjEntity();
            valueRep.save(new Value(favoriteObject, attRefToObj, objectId));

        } catch (NullPointerException e) {
            System.out.println("Favorite save exception : " + e.getMessage());
        }
    }

    @Override
    public void delete(String userId, String objectId) {
        try {
            Type favType = typeRep.findById(Constants.FAVORITE_TYPE_ID);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.USER_ID_ATTRIBUTE), favType).getAttribute();
            Attribute attRefToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE), favType).getAttribute();
            Collection<ObjEntity> favoriteObjects = objEntityRep.findByType(favType);

            ObjEntity favoriteObject = valueRep.getValueByObjEntityInAndValueAndAttributes(favoriteObjects, userId, attUserId).get().getObjEntity();
            valueRep.removeByObjEntityAndAttributesAndValue(favoriteObject, attRefToObj, objectId);

        } catch (NullPointerException e) {
            System.out.println("Favorite delete exception : " + e.getMessage());
        }
    }

    @Override
    public boolean check(String userId, String movieId) {
        boolean checkObjEntity = false;

        try {
            Type favType = typeRep.findById(Constants.FAVORITE_TYPE_ID);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.USER_ID_ATTRIBUTE), favType).getAttribute();
            Attribute attRefToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE), favType).getAttribute();
            Collection<ObjEntity> favoriteObjects = objEntityRep.findByType(favType);

            ObjEntity objOfUser = valueRep.getValueByObjEntityInAndValueAndAttributes(favoriteObjects, userId, attUserId).get().getObjEntity();
            if (valueRep.findByObjEntityAndAttributesAndValue(objOfUser, attRefToObj, movieId) != null)
                checkObjEntity = true;

        } catch (NullPointerException e) {
            System.out.println("Favorite check exception : " + e.getMessage());
        }

       return  checkObjEntity;
    }

    @Override
    public void create(String userId) {
        if (typeRep.findById(Constants.FAVORITE_TYPE_ID)!=null && attributeRep.findById(1)!=null) {
            Type favType = typeRep.findById(Constants.FAVORITE_TYPE_ID);
            Attribute attUserId = attributeRep.findById(Constants.USER_ID_ATTRIBUTE);

            ObjEntity object = objEntityRep.save(new ObjEntity(favType));
            valueRep.save(new Value(object, attUserId, userId));
        }
    }

    @Override
    public List<ObjEntity> get(String userId) {
        List<ObjEntity> favoriteList = new ArrayList<ObjEntity>();
        try {
            Type favType = typeRep.findById(Constants.FAVORITE_TYPE_ID);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.USER_ID_ATTRIBUTE), favType).getAttribute();
            Attribute attRefToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE), favType).getAttribute();
            Collection<ObjEntity> favoriteObjects = objEntityRep.findByType(favType);

            ObjEntity objectOfUser = valueRep.getValueByObjEntityInAndValueAndAttributes(favoriteObjects, userId, attUserId).get().getObjEntity();
            favoriteList = new ArrayList<ObjEntity>();

            for (Value value: valueRep.findByObjEntityAndAttributes(objectOfUser, attRefToObj)
                 ) {
                favoriteList.add(objEntityRep.findById(Integer.valueOf(value.getValue())));
            }

            return favoriteList;
        } catch (NullPointerException e) {
            System.out.println("Favorite get exception : " + e.getMessage());
        }
        return favoriteList;
    }
}
