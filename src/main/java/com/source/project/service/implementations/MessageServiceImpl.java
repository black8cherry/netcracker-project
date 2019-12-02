package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.repos.*;
import com.source.project.service.Constants;
import com.source.project.service.MessageService;
import com.source.project.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private ObjEntityRep objEntityRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private ValueService valueService;

    @Override
    public void create(Integer parentId, List<String> label, List<String> value, List<MultipartFile> file, String uploadPath) {
        System.out.println(file);
        try {
            Type messageType = typeRep.findById(Constants.MESSAGE_TYPE_ID);
            ObjEntity object = new ObjEntity(parentId, messageType);
            objEntityRep.save(object);

            for (int i = 0, j = 0; i < label.size(); i++) {
                if (label.get(i).equals(attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE).getLabel())) {
                    valueService.save(new Value(object,
                            attributeRep.findById(Constants.REFERENCE_TO_OBJECT_ATTRIBUTE),
                            String.valueOf(object.getId())));
                } else {
                    System.out.println(attributeRep.findByLabel(label.get(i)).getLabelType());
                    System.out.println(Constants.IMAGE_ATTRIBUTE_TYPE);
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
                        valueService.save(new Value(object,
                                attributeRep.findByLabel(label.get(i)),
                                value.get(i)));
                    }
                }
            }
        } catch (NullPointerException | IOException e) {
            System.out.println("Message create exception : " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer messageId) {
        for (Value val: valueRep.findAllByObjEntity(objEntityRep.findById(messageId))
        ) {
            valueService.remove(val);
        }

        objEntityRep.removeById(messageId);
    }

    @Override
    public List<Map<String, String>> getListMessages(Integer parentId) {
        List<Map<String, String>> reviewList = new ArrayList<Map<String, String>>();
        try {
            Type messageType = typeRep.findById(Constants.MESSAGE_TYPE_ID);
            List<ObjEntity> reviews = objEntityRep.findByTypeAndParentId(messageType, parentId);

            List<Attribute> attributesList = new ArrayList<Attribute>();
            List<TypeAttribute> typeAttributes = typeAttributeRep.findByType(messageType);
            for (TypeAttribute ta : typeAttributes
            ) {
                attributesList.add(ta.getAttribute());
            }


            for (ObjEntity object : reviews
            ) {
                Map<String, String> tmpMap = new HashMap<>();
                for (Attribute attribute : attributesList
                ) {
                    if (valueRep.findByAttributesAndObjEntity(attribute, object) == null)
                        tmpMap.put(attribute.getLabel(), " ");
                    else
                        tmpMap.put(attribute.getLabel(),
                                valueRep.findByAttributesAndObjEntity(attribute, object).getValue());
                }
                reviewList.add(tmpMap);
            }
        } catch (NullPointerException e) {
            System.out.println("Message getListMessage exception : " + e.getMessage());
        }
        return reviewList;
    }

    @Override
    public void edit(Integer messageId, List<String> label, List<String> value, List<MultipartFile> file, String uploadPath) {
        System.out.println(file);
        try {
            ObjEntity message = objEntityRep.findById(messageId);

            for(int i = 0, j = 0; i < label.size(); i++) {
                Value val = valueRep.findByAttributesAndObjEntity(
                        attributeRep.findByLabel(label.get(i)),
                        message);
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
            }

        } catch (NullPointerException | IOException e) {
            System.out.println("Message editMessage exception : " + e.getMessage());
        }
    }
}
