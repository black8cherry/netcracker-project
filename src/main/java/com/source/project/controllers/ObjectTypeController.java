package com.source.project.controllers;

import com.source.project.domain.Type;
import com.source.project.repos.TypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Transactional
@Controller
public class ObjectTypeController {

    @Autowired
    private TypeRep typeRep;

    @GetMapping("/objType")
    public String objTypeGet(
            Model model
    ) {
        model.addAttribute("objType", typeRep.findAll());
        return "objType";
    }

    @PostMapping("/objType")
    public String objTypePost(
            @RequestParam String type
    ) {
        if (typeRep.findByType(type) == null) {
            typeRep.save(new Type(type));
        }
        return "redirect:/objType";
    }

    @GetMapping("/delObjType/{id}")
    public String delObjType(
            @PathVariable("id") Integer id
    ) {
        if (typeRep.findById(id) != null) {
            typeRep.delete(typeRep.findById(id));
        }
        return "redirect:/objType";
    }
}
