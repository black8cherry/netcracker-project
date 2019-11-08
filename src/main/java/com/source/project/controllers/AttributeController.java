package com.source.project.controllers;

import com.source.project.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Transactional
@Controller
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute")
    public String AttributePage(
        @RequestParam Integer attributeId,
        @RequestParam(required = false) Integer typeId,
        Model model
    ) {
        model.addAttribute("attribute", attributeService.findById(attributeId));
        model.addAttribute("typeId", typeId);
        return "editAttribute";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute/edit")
    public String editAttribute(
            @RequestParam Integer attributeId,
            @RequestParam(required = false) String label,
            @RequestParam(required = false) String labelType,
            @RequestParam Integer typeId
    ) {
        attributeService.edit(attributeId, label, labelType);
        if(typeId!=null)
            return "redirect:/objType?typeId={typeId}";
        else
            return "redirect:/objType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editAttribute")
    public String saveAttribute(
            @RequestParam String label,
            @RequestParam String labelType
    ) {
        attributeService.save(label, labelType);

        return "redirect:/objType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/editAttribute/delete/{label}")
    public String deleteAttribute(
            @PathVariable("label") String label
    ) {

        attributeService.removeByLabel(label);
        return "redirect:/objType";
    }

}
