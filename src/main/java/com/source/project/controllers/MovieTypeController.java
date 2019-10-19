package com.source.project.controllers;

import com.source.project.domain.Type;
import com.source.project.repos.TypeRep;
import com.source.project.service.TypeService;
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
public class MovieTypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/objType")
    public String objTypeGet(
            Model model
    ) {
        model.addAttribute("objType", typeService.findAll());
        return "objType";
    }

    @PostMapping("/objType")
    public String objTypePost(
            @RequestParam String type
    ) {
        if (typeService.findByType(type) == null) {
            typeService.save(new Type(type));
        }
        return "redirect:/objType";
    }

    @GetMapping("/delObjType/{id}")
    public String delObjType(
            @PathVariable("id") Integer id
    ) {
        if (typeService.findById(id) != null) {
            typeService.delete(typeService.findById(id));
        }
        return "redirect:/objType";
    }
}
