package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.repos.*;
import com.source.project.service.Constants;
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

        for (ObjEntity obj: objEntityRep.findByParentId(id)
             ) {
            objEntityRep.removeById(obj.getId());
        }

        for (Value val: valueRep.findAllByObjEntity(objEntityRep.findById(id))
             ) {
            valueService.remove(val);
        }

        objEntityRep.removeById(id);
    }

    @Override
    public Integer save(String name,  Integer typeId) {
        ObjEntity objEntity = new ObjEntity(name, typeRep.findById(typeId));

        objEntityRep.save(objEntity);

        return objEntity.getId();
    }

    @Override
    public void edit(String objectName, List<String> label, List<String> value, Integer id, List<MultipartFile> file, String uploadPath) throws IOException {

        ObjEntity object = objEntityRep.findById(id);
        object.setName(objectName);
        for(int i = 0, j = 0; i < label.size(); i++) {
            if (valueRep.findByAttributesAndObjEntity(
                    attributeRep.findByLabel(label.get(i)),
                    object)!=null
            ) {
                Value val = valueRep.findByAttributesAndObjEntity(
                        attributeRep.findByLabel(label.get(i)),
                        object);
                if (attributeRep.findByLabel(label.get(i)).getLabelType().equals(Constants.IMAGE_ATTRIBUTE_TYPE)) {
                    if (file.get(j).getSize() != 0) {

                        if(!val.getValue().equals(Constants.NO_IMAGE)) {
                            File oldImg = new File(uploadPath + "/" + val.getValue());
                            oldImg.delete();
                        }

                        String uuidFile = UUID.randomUUID().toString();
                        String resultFilename = uuidFile + "." + file.get(j).getOriginalFilename();

                        file.get(j).transferTo(new File(uploadPath + "/" + resultFilename));
                        val.setValue(resultFilename);

                    }
                    j++;
                } else {
                    val.setValue(value.get(i));
                }
                valueService.save(val);
            } else {
                if (attributeRep.findByLabel(label.get(i)).getLabelType().equals(Constants.IMAGE_ATTRIBUTE_TYPE)) {
                    if (file.get(j).getSize() != 0) {

                        String uuidFile = UUID.randomUUID().toString();
                        String resultFilename = uuidFile + "." + file.get(j).getOriginalFilename();

                        file.get(j).transferTo(new File(uploadPath + "/" + resultFilename));

                        valueService.save(new Value(object, attributeRep.findByLabel(label.get(i)), resultFilename));
                    } else {
                        valueService.save(new Value(object, attributeRep.findByLabel(label.get(i)), Constants.NO_IMAGE));
                    }
                    j++;
                } else {
                    valueService.save(new Value(object, attributeRep.findByLabel(label.get(i)), value.get(i)));
                }
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
                if (valueRep.findByAttributesAndObjEntity(attribute, objEntity) == null)
                    attributeValue.put(attribute.getLabel(), "");
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

    @Override
    public boolean checkObjectForPage(Integer id) {
        boolean checkTypeForPage = false;
        try {
            for (Type type : typeRep.findTreeFromParent(Constants.VIDEO_OBJECT_TYPE_ID)
            ) {
                if (objEntityRep.findById(id).getType() == type)
                    checkTypeForPage = true;
            }
        } catch (NullPointerException e) {}
        return checkTypeForPage;
    }
}
