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
        model.addAttribute("fl", filmList);
        return "filmEdit";
    }

    @PostMapping("/{id}")
    public String filmSave(
            @RequestParam String objectName,
            //@ModelAttribute("fl")ArrayList<FilmList> filmL,
            @RequestParam List<String> label,
            @RequestParam List<String> value,
            @RequestParam("objectId") String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        List<Value> values = valueRep.findAllByObjects(object);
        List<TypeAttribute> typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(object.getType());
        //=============================================
        List<FilmList> filmL = new ArrayList<FilmList>();
        for(int i = 0; i < label.size(); i++) {
            filmL.add(new FilmList(label.get(i), value.get(i)));
        }

        // strange things
        /*
        System.out.println(label);
        System.out.println(value);
        System.out.println(filmL);
        */
        //----------------
        if(!values.isEmpty()){
            System.out.println("update values there");
            object.setName(objectName);
            for (FilmList tmp: filmL
            ) {
                Value vall = valueRep.findByAttributesAndObjects(attributeRep.findByLabel(tmp.getLabel()), object);
                vall.setValue(tmp.getValue());
                valueRep.save(vall);
            }
        }
        else {
            System.out.println("adding new values there");
            object.setName(objectName);

            for (FilmList tmp: filmL
            ) {
                values.add(new Value(object, attributeRep.findByLabel(tmp.getLabel()) , tmp.getValue()));
            }

            for (Value v: values
            ) {
                valueRep.save(v);
            }
        }
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


    @GetMapping("/{id}/edit/editAttribute")
    private String editAttributeGet(
            Model model
    ) {
        List<Attribute> attributes = attributeRep.findAll();
        model.addAttribute("attributes", attributes);
        return "editAttribute";
    }

    @PostMapping("/{id}/edit/editAttribute")
    private String editAttributePost(
            Model model,
            @RequestParam String label/*,
            @RequestParam Integer type*/
    ) {
        if(label != attributeRep.findByLabel(label).getLabel()) {
            Attribute attribute = new Attribute(label);
            attributeRep.save(attribute);
        }


        return "editAttribute";
    }



}
