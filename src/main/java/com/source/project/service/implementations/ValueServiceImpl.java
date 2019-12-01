package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Value;
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

    @Override
    public void save(Value value) {
            valueRep.save(value);
    }

    @Override
    public void findById(Integer id) {
        valueRep.removeById(id);
    }

    @Override
    public Value findByAttributesAndObjEntity(Attribute attribute, ObjEntity movie) {
        return valueRep.findByAttributesAndObjEntity(attribute, movie);
    }

    @Override
    public List<Value> getValuesByObjEntityInAndAttributes(Collection<ObjEntity> objEntity, Attribute attributes) {
        return valueRep.getValuesByObjEntityInAndAttributes(objEntity, attributes);
    }

    @Override
    public List<Value> getMainImages(List<ObjEntity> movies) {
        List<Value> images = new ArrayList<Value>();

        for (ObjEntity obj: movies
             ) {
            for (Value val: valueRep.findAllByObjEntity(obj)
                 ) {
                if (val.getAttributes().getLabelType().equals(Constants.IMAGE_ATTRIBUTE_TYPE)) {
                    images.add(val);
                    break;
                }
            }
        }

        return images;
    }

    @Override
    public Value getMainImage(ObjEntity movies) {

        for (Value val: valueRep.findAllByObjEntity(movies)
             ) {
            if (val.getAttributes().getLabelType().equals(Constants.IMAGE_ATTRIBUTE_TYPE)) {
                return val;
            }
        }
        return null;
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
