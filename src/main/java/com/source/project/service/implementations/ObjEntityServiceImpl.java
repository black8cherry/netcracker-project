package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.repos.*;
import com.source.project.service.ObjEntityService;
import com.source.project.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Service
public class ObjEntityServiceImpl implements ObjEntityService {

    @Autowired
    private ObjEntityRep objEntityRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private ValueService valueService;


    @Override
    public ObjEntity findById(Integer id) {
        return objEntityRep.findById(id);
    }

    @Override
    public void removeById(Integer id) {

        for (Value val: valueRep.findAllByObjEntity(objEntityRep.findById(id))
             ) {
            valueRep.delete(val);
        }

        objEntityRep.removeById(id);
    }

    @Override
    public Integer save(String name,  Integer typeId, MultipartFile file, String uploadPath) throws IOException {
        ObjEntity objEntity = new ObjEntity(name, typeRep.findById(typeId));
        if (file.getSize() != 0) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            objEntity.setFilename(resultFilename);
        } else {
            objEntity.setFilename("no-image.jpg");
        }

        objEntityRep.save(objEntity);

        return objEntity.getId();
    }

    @Override
    public void edit(String objectName, List<String> label, List<String> value, Integer id) {

        ObjEntity object = objEntityRep.findById(id);
        object.setName(objectName);

        for(int i = 0; i < label.size(); i++) {
            if (valueRep.findByAttributesAndObjEntity(
                    attributeRep.findByLabel(label.get(i)),
                    object)!=null
            ) {
                Value val = valueRep.findByAttributesAndObjEntity(
                        attributeRep.findByLabel(label.get(i)),
                        object);
                val.setValue(value.get(i));
                valueService.save(val);
            } else {
                valueService.save(new Value(object, attributeRep.findByLabel(label.get(i)), value.get(i)));
            }
        }

    }

    @Override
    public Map<String, String> showAttributes(ObjEntity objEntity) {

        Map<String, String > attributeValue = new HashMap<String, String>();

        List<Attribute> attributesList = new ArrayList<Attribute>();
        List<TypeAttribute> typeAttributes = new ArrayList<TypeAttribute>();
        List<Type> typeList = typeRep.findTreeFromChild(objEntity.getType().getId());
        for (Type type: typeList
        ) {
            typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(type);
            for (TypeAttribute ta: typeAttributes
            ) {
                attributesList.add(ta.getAttribute());
            }
        }

        for (Attribute attribute: attributesList
        ) {
            if(valueRep.findByAttributesAndObjEntity(attribute, objEntity)==null)
                attributeValue.put(attribute.getLabel(),"");
            else
                attributeValue.put(attribute.getLabel(),
                        valueRep.findByAttributesAndObjEntity(attribute, objEntity).getValue());
        }

        return attributeValue;
    }

    @Override
    public List<ObjEntity> findByNameIsContaining(String filter) {
        return objEntityRep.findByNameIsContaining(filter);
    }

    @Override
    public List<ObjEntity> getObjEntitiesByTypeInOrderByName(Collection<Type> type) {
        return objEntityRep.getObjEntitiesByTypeInOrderByName(type);
    }
}
