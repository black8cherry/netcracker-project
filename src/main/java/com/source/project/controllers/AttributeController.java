package com.source.project.controllers;

import com.source.project.domain.Attribute;
import com.source.project.domain.Objects;
import com.source.project.domain.TypeAttribute;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.ObjectsRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.TypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
public class AttributeController {

    @Autowired
    private ObjectsRep objectsRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private TypeRep typeRep;

    @GetMapping("/editAttribute")
    private String editAttributeGet(
        Model model
    ) {
        List<Attribute> attributes = attributeRep.findAll();
        List<TypeAttribute> attributes1 = typeAttributeRep.findAll(Sort.by("type"));
        model.addAttribute("typeAttributes", attributes1);
        model.addAttribute("attributes", attributes);


        model.addAttribute("filmType", typeRep.findById(1).getType());
        model.addAttribute("seriesType", typeRep.findById(2).getType());

        return "editAttribute";
    }

    @PostMapping("/editAttribute")
    private String editAttributePost(
            Model model,
            @RequestParam String label
    ) {
        if(attributeRep.findByLabel(label) == null) {
            attributeRep.save(new Attribute(label));
        }

        /*try {
            if (choose == 1) { //film
                if (typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findById(choose)) == null) {
                    typeAttributeRep.save(new TypeAttribute(typeRep.findById(choose), attributeRep.findByLabel(label)));
                }
            } else if (choose == 2) { //series
                if (typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findById(choose)) == null) {
                    typeAttributeRep.save(new TypeAttribute(typeRep.findById(choose), attributeRep.findByLabel(label)));
                }
            }
        } catch (NullPointerException e) {}*/

        return "redirect:/editAttribute";
    }

    @GetMapping("/editObjectAttributes/{type}")
    private String editObjAttGet(
            @PathVariable("type") String type,
            Model model
    ) {
        List<TypeAttribute> attributes = typeAttributeRep.findByType(typeRep.findByType(type));
        List<Attribute> attributesAll = attributeRep.findAll();
        List<Attribute> attributesNotInObj = new ArrayList<Attribute>();

        try {
            for (Attribute att : attributesAll
            ) {
                if (typeAttributeRep.findByAttributeAndType(att, typeRep.findByType(type)) == null) {

                    attributesNotInObj.add(att);
                }
            }
        } catch (NullPointerException e) {}

        model.addAttribute("attributes", attributes);
        model.addAttribute("mAttributes", attributesNotInObj);
        model.addAttribute("type", typeRep.findByType(type).getType());

        return "editObjAttribute";
    }

    /*@PostMapping("/main/{id}/editObjectAttributes")
    private String editObjAttPost(
            @RequestParam String label,
            @PathVariable("id") Integer id
    ) {
        if(attributeRep.findByLabel(label) == null) {
            attributeRep.save(new Attribute(label));
        }

        try {
            if (typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), objectsRep.findById(id).getType()) == null) {
                typeAttributeRep.save(new TypeAttribute(objectsRep.findById(id).getType(), attributeRep.findByLabel(label)));
                System.out.println("added");
            }
        } catch (NullPointerException e) {}

        System.out.println("not added");

        return "redirect:/main/{id}/editObjectAttributes";
    }*/

    @GetMapping("/editObjectAttributes/{type}/addTypeAtt/{label}")
    private String addTypeAtt(
            @PathVariable("label") String label,
            @PathVariable("type") String type,
            Model model
    ) {

        try {
            if (typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findByType(type)) == null) {
                typeAttributeRep.save(new TypeAttribute(typeRep.findByType(type), attributeRep.findByLabel(label)));
            }
        } catch (NullPointerException e) {}

        return "redirect:/editObjectAttributes/{type}";
    }

}
