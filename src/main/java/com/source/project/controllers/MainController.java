package com.source.project.controllers;

import com.source.project.domain.*;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.ObjectsRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.ValueRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
@RequestMapping(value = {"/main"})
public class MainController {

    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private ObjectsRep objectsRep;

    @GetMapping
    public String main(
            @RequestParam(required=false) String filter,
            Model model
    ) {
        Iterable<Objects> objects = objectsRep.findAll();

        if (filter != null && !filter.isEmpty()) {
            objects = objectsRep.findByNameOrderByName(filter);
        } else {
            objects = objectsRep.findAll(Sort.by("name"));
        }

        model.addAttribute("objects", objects);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit")
    public String edit(
            @PathVariable String id,
            Model model
    ) {
        Objects objects = objectsRep.findById(Integer.valueOf(id));
        //List<Value> values = valueRep.findAllByObjects(objects);
        List<TypeAttribute> typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(objects.getType());
        List<FilmList> filmList = new ArrayList<FilmList>();

        for (TypeAttribute tp: typeAttributes
        ) {
            String value;
            if(valueRep.findByAttributesAndObjects(tp.getAttribute(), objects) == null) {
                value = " ";
            }
            else {
                value = valueRep.findByAttributesAndObjects(tp.getAttribute(), objects).getValue();
            }
            filmList.add(new FilmList(tp.getAttribute().getLabel(), value));
        }


        model.addAttribute("objects", objects);
        model.addAttribute("val", filmList);
        return "filmEdit";
    }

    @PostMapping("/{id}")
    public String filmSave(
            @RequestParam String objectName,
            @RequestParam List<String> val,
            @RequestParam("objectId") String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        List<Value> values = valueRep.findAllByObjects(object);
        List<TypeAttribute> typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(object.getType());

        if(!values.isEmpty()){
            System.out.println("update values there");
            object.setName(objectName);
            int i = 0;
            for (Value v: values
            ) {
                v.setValue(val.get(i++));
            }
            i = 0;
        }
        else {
            System.out.println("adding new values there");
            object.setName(objectName);
            int i = 0;
            for (String tmp: val
            ) {
                values.add(new Value(object, typeAttributes.get(i++).getAttribute(), tmp));
            }
            i = 0;
            for (Value v: values
            ) {
                valueRep.save(v);
            }
        }
        //=======================================================


        //=======================================================
        objectsRep.save(object);

        return "redirect:/main/{id}";
    }

    @GetMapping("/{id}")
    public String filter(
            Model model,
            @PathVariable String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        List<Value> values = valueRep.findAllByObjects(object);

        //--------------------------------------------------------------

        List<TypeAttribute> typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(object.getType());
        List<FilmList> filmList = new ArrayList<FilmList>();

        for (TypeAttribute tp: typeAttributes
             ) {
            String value;

            if(valueRep.findByAttributesAndObjects(tp.getAttribute(), object) == null) {
                value = " ";
            }
            else {
                value = valueRep.findByAttributesAndObjects(tp.getAttribute(), object).getValue();
            }

            filmList.add(new FilmList(tp.getAttribute().getLabel(), value));
        }

        //--------------------------------------------------------------

        model.addAttribute("objects", object);
        /*model.addAttribute("att", att);
        model.addAttribute("val", values);*/
        model.addAttribute("fl", filmList);
        return "filmList";
    }

    @GetMapping("/delete/{id}")
    public String deleteObj(
            @PathVariable("id") String id
           // Model model
    ) {
        objectsRep.removeById(Integer.valueOf(id));
        //Iterable<Objects> object = objectsRep.findAll();
        //model.addAttribute("objects", object);
        return "redirect:/main";
    }
}
