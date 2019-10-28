package com.source.project.controllers;

import com.source.project.domain.Attribute;
import com.source.project.domain.TypeAttribute;
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
        model.addAttribute("types", typeService.findAll());

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
    @GetMapping("/editObjectAttributes/{typeId}")
    public String editObjAttGet(
            @PathVariable("typeId") Integer typeId,
            Model model
    ) {
        List<TypeAttribute> attributes = typeAttributeService.findByType(typeService.findById(typeId));
        List<Attribute> attributesAll = attributeService.findAll();
        List<Attribute> attributesNotInObj = new ArrayList<Attribute>();

        try {
            for (Attribute att : attributesAll
            ) {
                if (typeAttributeService.findByAttributeAndType(att, typeService.findById(typeId)) == null) {

                    attributesNotInObj.add(att);
                }
            }
        } catch (NullPointerException e) {}

        model.addAttribute("attributes", attributes);
        model.addAttribute("mAttributes", attributesNotInObj);
        model.addAttribute("type", typeService.findById(typeId));

        return "editObjAttribute";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editObjectAttributes/{typeId}/addTypeAtt/{label}")
    public String addTypeAtt(
            @PathVariable("label") String label,
            @PathVariable("typeId") Integer typeId,
            Model model
    ) {

        try {
            if (typeAttributeService.findByAttributeAndType(
                    attributeService.findByLabel(label),
                    typeService.findById(typeId)) == null) {
                typeAttributeService.save(new TypeAttribute(
                        typeService.findById(typeId),
                        attributeService.findByLabel(label)));
            }
        } catch (NullPointerException e) {}

        return "redirect:/editObjectAttributes/{typeId}";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute/delete/{label}")
    public String deleteAtr(
            @PathVariable("label") String label
    ) {

        attributeService.removeByLabel(label);
        return "redirect:/editAttribute";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editObjectAttributes/{typeId}/deleteTypeAtt/{label}")
    public String deleteTypeAtt(
            @PathVariable("typeId") Integer typeId,
            @PathVariable("label") String label
    ) {
        typeAttributeService.removeByAttributeAndType(
                attributeService.findByLabel(label),
                typeService.findById(typeId));
        return "redirect:/editObjectAttributes/{typeId}";
    }


}
