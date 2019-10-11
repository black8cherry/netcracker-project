package com.source.project.controllers;

import com.source.project.domain.Attribute;
import com.source.project.domain.Objects;
import com.source.project.domain.TypeAttribute;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.ObjectsRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.TypeRep;
import com.source.project.service.AttributeService;
import com.source.project.service.TypeAttributeService;
import com.source.project.service.TypeService;
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
    private AttributeService attributeService;
    @Autowired
    private TypeAttributeService typeAttributeService;
    @Autowired
    private TypeService typeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute")
    public String editAttributeGet(
        Model model
    ) {
        List<Attribute> attributes = attributeService.findAll();
        List<TypeAttribute> attributes1 = typeAttributeService.findAll(Sort.by("type"));
        model.addAttribute("typeAttributes", attributes1);
        model.addAttribute("attributes", attributes);


        model.addAttribute("filmType", typeService.findById(1).getType());
        model.addAttribute("seriesType", typeService.findById(2).getType());

        return "editAttribute";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editAttribute")
    public String editAttributePost(
            Model model,
            @RequestParam String label
    ) {
        if(attributeService.findByLabel(label) == null) {
            attributeService.save(new Attribute(label));
        }

        return "redirect:/editAttribute";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editObjectAttributes/{type}")
    public String editObjAttGet(
            @PathVariable("type") String type,
            Model model
    ) {
        List<TypeAttribute> attributes = typeAttributeService.findByType(typeService.findByType(type));
        List<Attribute> attributesAll = attributeService.findAll();
        List<Attribute> attributesNotInObj = new ArrayList<Attribute>();

        try {
            for (Attribute att : attributesAll
            ) {
                if (typeAttributeService.findByAttributeAndType(att, typeService.findByType(type)) == null) {

                    attributesNotInObj.add(att);
                }
            }
        } catch (NullPointerException e) {}

        model.addAttribute("attributes", attributes);
        model.addAttribute("mAttributes", attributesNotInObj);
        model.addAttribute("type", typeService.findByType(type).getType());

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
            if (typeAttributeService.findByAttributeAndType(
                    attributeService.findByLabel(label),
                    typeService.findByType(type)) == null) {
                typeAttributeService.save(new TypeAttribute(
                        typeService.findByType(type),
                        attributeService.findByLabel(label)));
            }
        } catch (NullPointerException e) {}

        return "redirect:/editObjectAttributes/{type}";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute/delete/{label}")
    public String deleteAtr(
            @PathVariable("label") String label
    ) {
        if(typeAttributeService.findByAttributeAndType(
                attributeService.findByLabel(label),
                typeService.findById(1)) != null
           || typeAttributeService.findByAttributeAndType(
                attributeService.findByLabel(label),
                typeService.findById(2)) != null) {
            typeAttributeService.removeByAttribute(attributeService.findByLabel(label));
        }
        attributeService.removeByLabel(label);
        return "redirect:/editAttribute";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editObjectAttributes/{type}/deleteTypeAtt/{label}")
    public String deleteTypeAtt(
            @PathVariable("type") String type,
            @PathVariable("label") String label
    ) {
        typeAttributeService.removeByAttributeAndType(
                attributeService.findByLabel(label),
                typeService.findByType(type));
        return "redirect:/editObjectAttributes/{type}";
    }


}
