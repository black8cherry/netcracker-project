package com.source.project.controllers;

import com.source.project.domain.Attribute;
import com.source.project.domain.TypeAttribute;
import com.source.project.repos.AttributeRep;
import com.source.project.repos.TypeAttributeRep;
import com.source.project.repos.TypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


@Controller
@RequestMapping("/editAttribute")
public class AttributeController {

    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private TypeRep typeRep;

    @GetMapping
    private String editAttributeGet(
        Model model
    ) {
        List<Attribute> attributes = attributeRep.findAll();
        List<TypeAttribute> attributes1 = typeAttributeRep.findAll(Sort.by("type"));
        //System.out.println(attributes1);
        model.addAttribute("typeAttributes", attributes1);
        model.addAttribute("attributes", attributes);

        return "editAttribute";
    }

    @PostMapping
    private String editAttributePost(
            Model model,
            @RequestParam String label,
            @RequestParam Integer choose
    ) {
        if(attributeRep.findByLabel(label) == null) {
            attributeRep.save(new Attribute(label));
        }

        try {
            if (choose == 1) { //film
                if (typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findById(choose)) == null) {
                    typeAttributeRep.save(new TypeAttribute(typeRep.findById(choose), attributeRep.findByLabel(label)));
                }
            } else if (choose == 2) { //series
                if (typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findById(choose)) == null) {
                    typeAttributeRep.save(new TypeAttribute(typeRep.findById(choose), attributeRep.findByLabel(label)));
                }
            }
        } catch (NullPointerException e) {}

        return "redirect:/editAttribute";
    }



}
