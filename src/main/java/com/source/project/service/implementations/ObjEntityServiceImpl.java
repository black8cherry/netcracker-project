package com.source.project.service.implementations;

import com.source.project.domain.*;
import com.source.project.domain.resources.FilmListConnector;
import com.source.project.repos.*;
import com.source.project.service.ObjEntityService;
import com.source.project.service.ValueService;
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
    @Autowired
    private ValueService valueService;


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

        List<FilmListConnector> filmL = new ArrayList<FilmListConnector>();
        for(int i = 0; i < label.size(); i++) {
            filmL.add(new FilmListConnector(label.get(i), value.get(i)));
        }

        if(!values.isEmpty()){
            objEntity.setName(objectName);
            for (FilmListConnector tmp: filmL
            ) {
                Value val = valueRep.findByAttributesAndObjEntity(
                        attributeRep.findByLabel(tmp.getLabel()),
                        objEntity);
                val.setValue(tmp.getValue());
                valueService.save(val);
            }
        }
        else {
            objEntity.setName(objectName);
            for (FilmListConnector tmp: filmL
            ) {
                values.add(new Value(objEntity, attributeRep.findByLabel(tmp.getLabel()) , tmp.getValue()));
            }
            for (Value val: values
            ) {
                valueService.save(val);
            }
        }
        objEntityRep.save(objEntity);
    }

    @Override
    public List<FilmListConnector> showAttributes(ObjEntity objEntity) {
        Integer childId = objEntity.getType().getId();
        List<FilmListConnector> filmListConnector = new ArrayList<FilmListConnector>();
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
                filmListConnector.add(new FilmListConnector(tp.getAttribute().getLabel(), value));
            }
        }
        return filmListConnector;
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
