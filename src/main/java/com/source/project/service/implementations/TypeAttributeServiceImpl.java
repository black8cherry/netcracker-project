package com.source.project.service.implementations;

import com.source.project.domain.Attribute;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.TypeAttribute;
import com.source.project.repos.*;
import com.source.project.service.Constants;
import com.source.project.service.TypeAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class TypeAttributeServiceImpl implements TypeAttributeService {

    @org.springframework.beans.factory.annotation.Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private ObjEntityRep objEntityRep;
    @Autowired
    private ValueRep valueRep;

    @Override
    public List<TypeAttribute> findByType(Type type) {
        return typeAttributeRep.findByType(type);
    }

    @Override
    public void removeByAttributeAndType(Attribute attribute, Type type) {
        for (ObjEntity obj: objEntityRep.findByType(type)
             ) {
            for (com.source.project.domain.Value val: valueRep.findByObjEntityAndAttributes(obj, attribute)
                 ) {
                if (attribute.getLabelType().equals(Constants.IMAGE_ATTRIBUTE_TYPE) && !val.getValue().equals(Constants.NO_IMAGE)) {
                    File file = new File(uploadPath + "/" + val.getValue());
                    file.delete();
                }
                valueRep.delete(val);
            }
        }
        typeAttributeRep.removeByAttributeAndType(attribute, type);
    }

    @Override
    public void save(String label, Integer typeId) {

        if (typeAttributeRep.findByAttributeAndType(
                attributeRep.findByLabel(label),
                typeRep.findById(typeId)) == null
        ) {
            typeAttributeRep.save(new TypeAttribute(
                    typeRep.findById(typeId),
                    attributeRep.findByLabel(label)));
        }
    }
}
