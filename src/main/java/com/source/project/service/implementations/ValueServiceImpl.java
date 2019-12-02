package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Value;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.ValueRep;
import com.source.project.service.Constants;
import com.source.project.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ValueServiceImpl implements ValueService {

    @org.springframework.beans.factory.annotation.Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private AttributeRep attributeRep;

    @Override
    public void save(Value value) {
            valueRep.save(value);
    }

    @Override
    public void findById(Integer id) {
        valueRep.removeById(id);
    }

    @Override
    public List<Value> getMainImages(List<ObjEntity> movies) {
        List<Value> images = new ArrayList<Value>();
        try {
            Attribute mainImg = attributeRep.findById(Constants.MAIN_IMAGE_ID);
            for (ObjEntity obj : movies
            ) {
                if (valueRep.findByAttributesAndObjEntity(mainImg, obj) != null)
                    images.add(valueRep.findByAttributesAndObjEntity(mainImg, obj));
                else
                    images.add(new Value(obj, mainImg, null));
            }
        } catch (NullPointerException e) {}
        return images;
    }

    @Override
    public Value getMainImage(ObjEntity movie) {
        try {
            Attribute mainImg = attributeRep.findById(Constants.MAIN_IMAGE_ID);
            if (valueRep.findByAttributesAndObjEntity(mainImg, movie) != null)
                return valueRep.findByAttributesAndObjEntity(mainImg, movie);
            else
                return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void remove(Value val) {
        if(val.getAttributes().getLabelType().equals(Constants.IMAGE_ATTRIBUTE_TYPE) && !val.getValue().equals(Constants.NO_IMAGE)) {
            File file = new File(uploadPath + "/" + val.getValue());
            file.delete();
        }
        valueRep.delete(val);
    }
}
