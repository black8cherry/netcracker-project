package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.domain.ObjEntity;
import com.source.project.repos.*;
import com.source.project.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

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
    private TypeAttributeRep typeAttributeRep;

    @Override
    public void save(String userId, String objectId) {
        try {
            Type favType = typeRep.findById(2);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(1), favType).getAttribute();
            Attribute attRefToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(2), favType).getAttribute();
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
            Type favType = typeRep.findById(2);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(1), favType).getAttribute();
            Attribute attRefToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(2), favType).getAttribute();
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
            Type favType = typeRep.findById(2);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(1), favType).getAttribute();
            Attribute attRefToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(2), favType).getAttribute();
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
        if (typeRep.findById(2)!=null && attributeRep.findById(1)!=null) {
            Type favType = typeRep.findById(2);
            Attribute attUserId = attributeRep.findById(1);

            ObjEntity object = objEntityRep.save(new ObjEntity(favType));
            valueRep.save(new Value(object, attUserId, userId));
        }
    }

    @Override
    public List<ObjEntity> get(String userId) {
        List<ObjEntity> favoriteList = new ArrayList<ObjEntity>();
        try {
            Type favType = typeRep.findById(2);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(1), favType).getAttribute();
            Attribute attRefToObj = typeAttributeRep.findByAttributeAndType(attributeRep.findById(2), favType).getAttribute();
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
