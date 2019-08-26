package com.source.project.controllers;

import com.source.project.domain.Objects;
import com.source.project.repos.ObjectsRep;
import com.source.project.repos.TypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class AddController {

    @Autowired
    private ObjectsRep objectsRep;
    @Autowired
    private TypeRep typeRep;

    @GetMapping("/add_film")
    public String add_main(
            Model model
    ) {
        Iterable<Objects> objects = objectsRep.findAll();
        model.addAttribute("objects", objects);
        return "add_film";
    }

    @PostMapping("/add_film")
    public String add(
            @RequestParam String name,
            @RequestParam Integer type,
            Model model
    ) {
        Objects object = new Objects(name, typeRep.findById(type));
        objectsRep.save(object);
        Iterable<Objects> objects = objectsRep.findAll();
        model.addAttribute("objects", objects);
        return "add_film";
    }

}
