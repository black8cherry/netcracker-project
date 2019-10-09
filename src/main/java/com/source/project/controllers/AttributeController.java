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

@Transactional
@Controller
public class AttributeController {


    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private TypeRep typeRep;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute")
    public String editAttributeGet(
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editAttribute")
    public String editAttributePost(
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editObjectAttributes/{type}")
    public String editObjAttGet(
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editObjectAttributes/{type}/addTypeAtt/{label}")
    public String addTypeAtt(
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


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute/delete/{label}")
    public String deleteAtr(
            @PathVariable("label") String label
    ) {
        if(typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findById(1)) != null
                || typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findById(2)) != null) {
            typeAttributeRep.removeByAttribute(attributeRep.findByLabel(label));
        }
        attributeRep.removeByLabel(label);
        return "redirect:/editAttribute";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editObjectAttributes/{type}/deleteTypeAtt/{label}")
    public String deleteTypeAtt(
            @PathVariable("type") String type,
            @PathVariable("label") String label
    ) {
        typeAttributeRep.removeByAttributeAndType(attributeRep.findByLabel(label), typeRep.findByType(type));
        return "redirect:/editObjectAttributes/{type}";
    }


}
