package com.source.project.controllers;

import com.source.project.domain.*;
import com.source.project.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
@RequestMapping(value = {"/main"})
public class ObjectController {
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private ObjectsRep objectsRep;
    @Autowired
    private UserRep userRep;

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

        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null) {
            Authentication authentication = context.getAuthentication();
            if(!(authentication instanceof AnonymousAuthenticationToken)) {
                User user = (User) authentication.getPrincipal();
                String role = user.getRole().toString();
                System.out.println("role = " + role);
                model.addAttribute("role", role);
            }
        }
        //=====================================================================
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
        model.addAttribute("fl", filmList);
        return "filmList";
    }
}
