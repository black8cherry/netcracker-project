package com.source.project.controllers;

import com.source.project.domain.Atribute;
import com.source.project.domain.Objects;
import com.source.project.domain.Value;
import com.source.project.repos.AtributeRep;
import com.source.project.repos.ObjectsRep;
import com.source.project.repos.ValueRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
@Controller
@RequestMapping("/main")
public class MainController {

    @Autowired
    private ValueRep valueRep;
    @Autowired
    private AtributeRep atributeRep;
    @Autowired
    private ObjectsRep objectsRep;

    @GetMapping
    public String main(
            @RequestParam(required=false) String filter,
            Model model
    ) {
        Iterable<Objects> objects = objectsRep.findAll();

        if (filter != null && !filter.isEmpty()) {
            objects = objectsRep.findByName(filter);
        } else {
            objects = objectsRep.findAll();
        }

        model.addAttribute("objects", objects);
        model.addAttribute("filter", filter);
        return "main";
    }

    @GetMapping("/{id}")
    public String filter(
            //@PathVariable Objects objects,
            Model model,
            @PathVariable String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        List<Value> values = valueRep.findAllByObjects(object);
        List<Atribute> atributes = atributeRep.findAllByValues(values);
        model.addAttribute("objects", object);
        model.addAttribute("att", atributes);
        model.addAttribute("att", values);
        return "filmList";
    }

    @PostMapping
    public String filmSave(
            @RequestParam String objectName,
            @RequestParam("objectId") String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        object.setName(objectName);
        objectsRep.save(object);
        return "redirect:/main";
    }

    @GetMapping("/delete/{id}")
    public String deleteObj(
            @PathVariable("id") String id
           // Model model
    ) {
        objectsRep.removeById(Integer.valueOf(id));
        //Iterable<Objects> object = objectsRep.findAll();
        //model.addAttribute("objects", object);
        return "redirect:/main";
    }
}
