package com.source.project.controllers;

import com.source.project.service.AttributeService;
import com.source.project.service.TypeAttributeService;
import com.source.project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Transactional
@Controller
public class ObjectAttributeController {

    @Autowired
    private AttributeService attributeService;
    @Autowired
    private TypeAttributeService typeAttributeService;
    @Autowired
    private TypeService typeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editObjectAttributes/{typeId}")
    public String ObjectAttributesPage(
            @PathVariable("typeId") Integer typeId,
            Model model
    ) {
        model.addAttribute("parentAttributes", attributeService.getParentAtt(typeId));
        model.addAttribute("attributes", typeAttributeService.findByType(typeService.findById(typeId)));
        model.addAttribute("attributesNotInObject", attributeService.attributesNotInObj(typeId));
        model.addAttribute("type", typeService.findById(typeId));

        return "editObjAttribute";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/editObjectAttributes/{typeId}/addTypeAtt/{label}")
    public String saveTypeAttribute(
            @PathVariable("label") String label,
            @PathVariable("typeId") Integer typeId
    ) {
        typeAttributeService.save(label, typeId);

        return "redirect:/objType?typeId={typeId}";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/editObjectAttributes/{typeId}/deleteTypeAtt/{label}")
    public String deleteTypeAttribute(
            @PathVariable("typeId") Integer typeId,
            @PathVariable("label") String label
    ) {
        try {
            typeAttributeService.removeByAttributeAndType(
                    attributeService.findByLabel(label),
                    typeService.findById(typeId));
            return "redirect:/objType?typeId={typeId}";
        } catch (NullPointerException e) {
            return "errorPage";
        }
    }
}
