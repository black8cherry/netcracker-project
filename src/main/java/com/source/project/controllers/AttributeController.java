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
    public String AttributePage(
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
    public String saveAttribute(
            @RequestParam String label,
            @RequestParam String labelType
    ) {
        attributeService.save(label, labelType);

        return "redirect:/objType";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editAttribute/delete/{label}")
    public String deleteAttribute(
            @PathVariable("label") String label
    ) {

        attributeService.removeByLabel(label);
        return "redirect:/objType";
    }

}
