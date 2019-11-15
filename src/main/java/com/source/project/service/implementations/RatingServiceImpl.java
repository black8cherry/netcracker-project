package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.repos.*;
import com.source.project.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

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
    public void rate(String userId, Integer parentId, Float value) {
        if(value != null) {
            try {
                Type rateType = typeRep.findById(4);
                Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(1), rateType).getAttribute();
                Attribute attRate = typeAttributeRep.findByAttributeAndType(attributeRep.findById(4), rateType).getAttribute();

                List<ObjEntity> rateList = objEntityRep.findByTypeAndParentId(rateType, parentId);

                if (valueRep.getValueByObjEntityInAndValueAndAttributes(rateList, userId, attUserId).isPresent()) {

                    ObjEntity userObject = valueRep.getValueByObjEntityInAndValueAndAttributes(rateList, userId, attUserId).get().getObjEntity();
                    valueRep.findByAttributesAndObjEntity(attRate, userObject).setValue(String.valueOf(value));

                } else {
                    ObjEntity rateObject = new ObjEntity(parentId, rateType);
                    objEntityRep.save(rateObject);
                    valueRep.save(new Value(rateObject, attUserId, userId));
                    valueRep.save(new Value(rateObject, attRate, String.valueOf(value)));
                }

            } catch (NullPointerException e) {
                System.out.println("Rating save exception : " + e.getMessage());
            }
        }
    }

    @Override
    public void rerate(String userId, Integer parentId, Float value) {
        try {
            Type rateType = typeRep.findById(4);
            Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(1), rateType).getAttribute();
            Attribute attRate = typeAttributeRep.findByAttributeAndType(attributeRep.findById(4), rateType).getAttribute();

            Collection<ObjEntity> rateObjects = objEntityRep.findByTypeAndParentId(rateType, parentId);

            ObjEntity objOfUser = valueRep.getValueByObjEntityInAndValueAndAttributes(rateObjects, userId, attUserId).get().getObjEntity();

            Value usr = valueRep.findByAttributesAndObjEntity(attUserId, objOfUser);
            Value rat = valueRep.findByAttributesAndObjEntity(attRate, objOfUser);

            valueRep.removeById(usr.getId());
            valueRep.removeById(rat.getId());
            objEntityRep.removeById(objOfUser.getId());

            objOfUser = new ObjEntity(parentId, rateType);
            usr = new Value(objOfUser, attUserId, userId);
            rat = new Value(objOfUser, attRate, String.valueOf(value));

            objEntityRep.save(objOfUser);
            valueRep.save(usr);
            valueRep.save(rat);
        } catch (NullPointerException e) {
            System.out.println("Rating rerate exception : " + e.getMessage());
        }
    }

    @Override
    public String getRate(Integer id) {
        float result = 0;
        String res = "0";
        try {
            List<ObjEntity> rateL = objEntityRep.findByTypeAndParentId(typeRep.findById(4), id);

            for (ObjEntity obj : rateL
            ) {
                result += Float.parseFloat(valueRep.findByAttributesAndObjEntity(attributeRep.findById(4), obj).getValue());
            }

            if (result != 0) {
                res = new DecimalFormat("#0.00").format(result / objEntityRep.countByTypeAndParentId(typeRep.findById(4), id));
            }
        } catch (NullPointerException e) {
            System.out.println("Rating getRate exception : " + e.getMessage());
        }
        return res;
    }

    @Override
    public boolean check(Integer objId, String userId) {
        boolean check = false;
        try {
            List<ObjEntity> rateL = objEntityRep.findByTypeAndParentId(
                    typeRep.findById(4),
                    objId
            );

            if (valueRep.getValueByObjEntityInAndValueAndAttributes(
                    rateL,
                    userId,
                    attributeRep.findById(1)).isPresent()) {
                check = true;
            }
        } catch (NullPointerException e) {
            System.out.println("Rating check exception : " + e.getMessage());
        }
        return check;
    }
}
