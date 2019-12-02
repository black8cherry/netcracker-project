package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.Value;
import com.source.project.repos.*;
import com.source.project.service.Constants;
import com.source.project.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

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
                Type rateType = typeRep.findById(Constants.RATING_TYPE_ID);
                Attribute attUserId = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.USER_ID_ATTRIBUTE), rateType).getAttribute();
                Attribute attRate = typeAttributeRep.findByAttributeAndType(attributeRep.findById(Constants.RATE_ATTRIBUTE), rateType).getAttribute();

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
    public String getRate(Integer id) {
        float result = 0;
        String res = "0";
        try {
            List<ObjEntity> rateL = objEntityRep.findByTypeAndParentId(typeRep.findById(Constants.RATING_TYPE_ID), id);

            for (ObjEntity obj : rateL
            ) {
                result += Float.parseFloat(valueRep.findByAttributesAndObjEntity(attributeRep.findById(Constants.RATE_ATTRIBUTE), obj).getValue());
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
                    typeRep.findById(Constants.RATING_TYPE_ID),
                    objId
            );

            if (valueRep.getValueByObjEntityInAndValueAndAttributes(
                    rateL,
                    userId,
                    attributeRep.findById(Constants.USER_ID_ATTRIBUTE)).isPresent()) {
                check = true;
            }
        } catch (NullPointerException e) {
            System.out.println("Rating check exception : " + e.getMessage());
        }
        return check;
    }
}
