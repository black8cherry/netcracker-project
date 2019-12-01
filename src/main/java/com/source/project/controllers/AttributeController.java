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
        try {
            model.addAttribute("attribute", attributeService.findById(attributeId));
            model.addAttribute("typeId", typeId);
            return "editAttribute";
        } catch (Exception e) {
            return "errorPage";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute/edit")
    public String editAttribute(
            @RequestParam Integer attributeId,
            @RequestParam String label,
            @RequestParam String oldLabelType,
            @RequestParam(required = false) String labelType,
            @RequestParam(required = false) Integer typeId
    ) {
        try {
            if (labelType==null)
                attributeService.edit(attributeId, label, oldLabelType);
            else
                attributeService.edit(attributeId, label, labelType);
            if(typeId!=null)
                return "redirect:/objType?typeId="+ typeId;
            else
                return "redirect:/objType";
        } catch (Exception e) {
            return "errorPage";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/editAttribute")
    public String saveAttribute(
            @RequestParam String label,
            @RequestParam String labelType,
            @RequestParam(required = false) Integer typeId
    ) {
        try {
            if (!label.isEmpty())
                attributeService.save(label, labelType);
            if (typeId==null)
                return "redirect:/objType";
            else
                return "redirect:/objType?typeId="+typeId;
        } catch (Exception e) {
            return "errorPage";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/editAttribute/delete/{id}")
    public String deleteAttribute(
            @PathVariable("id") Integer id,
            @RequestParam(required = false) Integer typeId
    ) {
        try {
            attributeService.removeById(id);
            if (typeId==null)
                return "redirect:/objType";
            else
                return "redirect:/objType?typeId="+typeId;
        } catch (Exception e) {
            return "errorPage";
        }
    }

}
