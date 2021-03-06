package com.source.project.controllers;

import com.source.project.service.AttributeService;
import com.source.project.service.TypeAttributeService;
import com.source.project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Transactional
@Controller
public class ObjectEntityTypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private TypeAttributeService typeAttributeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/objType")
    public String objectTypePage(
            @RequestParam(required = false) Integer typeId,
            Model model
    ) {
        if(typeId!=null) {
            model.addAttribute("attributesWithParent", attributeService.getParentAtt(typeId));
            model.addAttribute("attributesInObject", typeAttributeService.findByType(typeService.findById(typeId)));
            model.addAttribute("attributesNotInObject", attributeService.attributesNotInObj(typeId));
            model.addAttribute("attributesType", typeService.findById(typeId));
        }
        model.addAttribute("allAttributes", attributeService.findAll());
        model.addAttribute("parentType", typeService.findByParentIdIsNull());
        model.addAttribute("childType", typeService.findByParentIdIsNotNull());
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("objType", typeService.findAll());
        return "objType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/objType")
    public String saveObjectType(
            @RequestParam String typename,
            @RequestParam(required=false) Integer parentId
    ) {

        if (typename != null && !typename.isEmpty()) {
            if(parentId!=null) {
                typeService.save(parentId, typename);
            } else {
                typeService.save(typename);
            }
        }
        return "redirect:/objType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/delObjType/{id}")
    public String deleteObjectType(
            @PathVariable("id") Integer id
    ) {
        try {
            if (typeService.findById(id) != null) {
                typeService.delete(id);
            }
            return "redirect:/objType";
        } catch (NullPointerException e) {
            return "errorPage";
        }
    }
}
