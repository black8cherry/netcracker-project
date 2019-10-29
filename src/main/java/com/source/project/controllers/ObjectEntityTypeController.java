package com.source.project.controllers;

import com.source.project.service.AttributeService;
import com.source.project.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Transactional
@Controller
public class ObjectEntityTypeController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private AttributeService attributeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/objType")
    public String objTypeGet(
            @RequestParam(required = false) Integer typeId,
            Model model
    ) {
        if(typeId!=null) {
            model.addAttribute("attributes", attributeService.getParentAtt(typeId));
        }
        model.addAttribute("parentType", typeService.findByParentIdIsNull());
        model.addAttribute("childType", typeService.findByParentIdIsNotNull());
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("objType", typeService.findAll());
        return "objType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/objType")
    public String objTypePost(
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
    @GetMapping("/delObjType/{id}")
    public String delObjType(
            @PathVariable("id") Integer id
    ) {
        if (typeService.findById(id) != null) {
            typeService.delete(id);
        }
        return "redirect:/objType";
    }
}
