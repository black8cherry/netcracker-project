package com.source.project.controllers;

import com.source.project.domain.*;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.ObjectsRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.ValueRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @PostMapping
    public String filmSave(
            @RequestParam String objectName,
            @RequestParam("objectId") String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        object.setName(objectName);
        objectsRep.save(object);
        return "redirect:/main";
    }

    @GetMapping("/{id}")
    public String filter(
            //@PathVariable Objects objects,
            Model model,
            @PathVariable String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        List<Value> values = valueRep.findAllByObjects(object);

        /*List<Value> val = values;
        List<Attribute> att = new ArrayList<Attribute>();
        for (Value val1:
             val) {
                att.add(val1.getAttributes());
        }*/

        //--------------------------------------------------------------

        List<TypeAttribute> typeAttributes = typeAttributeRep.findByType(object.getType());
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
        //System.out.println("ATTRIBUTES : " + att);
       // List<Atribute> atributes = atributeRep.findAllByValues(val);

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
