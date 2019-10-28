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

    @Override
    public void save(String userId, Integer parentId, Float value) {
        if(value != null) {
            Type rating = typeRep.findById(4);
            Attribute attUsr = attributeRep.findById(1);
            Attribute attVal = attributeRep.findById(4);

            ObjEntity obj = new ObjEntity(parentId, rating);
            Value usr = new Value(obj, attUsr, userId);
            Value rat = new Value(obj, attVal, String.valueOf(value));

            objEntityRep.save(obj);
            valueRep.save(usr);
            valueRep.save(rat);
        }
    }

    @Override
    public void rerate(String userId, Integer parentId, Float value) {
        Type rating = typeRep.findById(4);
        Attribute attUsr = attributeRep.findById(1);
        Attribute attVal = attributeRep.findById(4);

        Collection<ObjEntity> movies = objEntityRep.findByTypeAndParentId(rating, parentId);

        ObjEntity objOfUser = valueRep.getValueByObjEntityInAndValueAndAttributes(movies, userId, attUsr).get().getObjEntity();

        Value usr = valueRep.findByAttributesAndObjEntity(attUsr, objOfUser);
        Value rat = valueRep.findByAttributesAndObjEntity(attVal, objOfUser);

        valueRep.removeById(usr.getId());
        valueRep.removeById(rat.getId());
        objEntityRep.removeById(objOfUser.getId());

        objOfUser = new ObjEntity(parentId, rating);
        usr = new Value(objOfUser, attUsr, userId);
        rat = new Value(objOfUser, attVal, String.valueOf(value));

        objEntityRep.save(objOfUser);
        valueRep.save(usr);
        valueRep.save(rat);
    }

    @Override
    public String getRate(Integer id) {
        float result = 0;
        List<ObjEntity> rateL = objEntityRep.findByTypeAndParentId(
                typeRep.findById(4),
                id
        );
        for (ObjEntity obj: rateL
             ) {
            result += Float.parseFloat(valueRep.findByAttributesAndObjEntity(attributeRep.findById(4), obj).getValue());
        }

        String res = "0";

        if(result != 0) {
            res = new DecimalFormat("#0.00")
                    .format(result / objEntityRep.countByTypeAndParentId(
                            typeRep.findById(4),
                            id));
        }
        return res;
    }

    @Override
    public boolean findByObjectsAndUser(Integer objId, String userId) {
        boolean check = false;
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
        return check;
    }
}
