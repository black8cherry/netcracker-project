package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.domain.resources.FilmList;
import com.source.project.repos.*;
import com.source.project.service.ObjEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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


    @Override
    public ObjEntity findById(Integer id) {
        return objEntityRep.findById(id);
    }

    @Override
    public void removeById(Integer id) {
        objEntityRep.removeById(id);
    }

    @Override
    public List<ObjEntity> findAll() {
        return objEntityRep.findAll();
    }

    @Override
    public void save(String name,  Integer typeId, MultipartFile file, String uploadPath) throws IOException {
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
    }

    @Override
    public void edit(String objectName, List<String> label, List<String> value, Integer id) {
        ObjEntity objEntity = objEntityRep.findById(id);
        List<Value> values = valueRep.findAllByObjEntity(objEntity);

        List<FilmList> filmL = new ArrayList<FilmList>();
        for(int i = 0; i < label.size(); i++) {
            filmL.add(new FilmList(label.get(i), value.get(i)));
        }

        if(!values.isEmpty()){
            objEntity.setName(objectName);
            for (FilmList tmp: filmL
            ) {
                Value vall = valueRep.findByAttributesAndObjEntity(
                        attributeRep.findByLabel(tmp.getLabel()),
                        objEntity);
                vall.setValue(tmp.getValue());
                valueRep.save(vall);
            }
        }
        else {
            objEntity.setName(objectName);
            for (FilmList tmp: filmL
            ) {
                values.add(new Value(objEntity, attributeRep.findByLabel(tmp.getLabel()) , tmp.getValue()));
            }
            for (Value v: values
            ) {
                valueRep.save(v);
            }
        }
        objEntityRep.save(objEntity);
    }

    @Override
    public List<FilmList> showAttributes(ObjEntity objEntity) {
        Integer childId = objEntity.getType().getId();
        List<FilmList> filmList = new ArrayList<FilmList>();
        List<TypeAttribute> typeAttributes = new ArrayList<TypeAttribute>();
        List<Type> typeList = typeRep.findTreeFromChild(childId);
        for (Type type: typeList
             ) {
             typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(type);
            for (TypeAttribute tp: typeAttributes
            ) {
                String value;
                if(valueRep.findByAttributesAndObjEntity(tp.getAttribute(), objEntity) == null) {
                    value = " ";
                }
                else {
                    value = valueRep.findByAttributesAndObjEntity(tp.getAttribute(), objEntity).getValue();
                }
                filmList.add(new FilmList(tp.getAttribute().getLabel(), value));
            }
        }
        return filmList;
    }

    @Override
    public List<ObjEntity> findByNameIsContaining(String filter) {
        return objEntityRep.findByNameIsContaining(filter);
    }

    @Override
    public List<ObjEntity> findAll(Sort sort) {
        return objEntityRep.findAll(sort);
    }


    @Override
    public List<ObjEntity> getObjEntitiesByTypeInOrderByName(Collection<Type> type) {
        return objEntityRep.getObjEntitiesByTypeInOrderByName(type);
    }
}
