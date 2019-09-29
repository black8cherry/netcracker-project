package com.source.project.controllers;

import com.source.project.domain.*;
import com.source.project.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
//@RequestMapping(value = {"/main"})
public class ObjectController {
    @Autowired
    private TypeAttributeRep typeAttributeRep;
    @Autowired
    private ValueRep valueRep;
    @Autowired
    private AttributeRep attributeRep;
    @Autowired
    private ObjectsRep objectsRep;
    @Autowired
    private UserRep userRep;
    @Autowired
    private TypeRep typeRep;
    @Autowired
    private FavoritesRep favoritesRep;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/main/{id}/edit")
    public String edit(
            @PathVariable String id,
            Model model
    ) {
        Objects objects = objectsRep.findById(Integer.valueOf(id));
        //List<Value> values = valueRep.findAllByObjects(objects);
        List<TypeAttribute> typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(objects.getType());
        List<FilmList> filmList = new ArrayList<FilmList>();

        for (TypeAttribute tp: typeAttributes
        ) {
            String value;
            if(valueRep.findByAttributesAndObjects(tp.getAttribute(), objects) == null) {
                value = " ";
            }
            else {
                value = valueRep.findByAttributesAndObjects(tp.getAttribute(), objects).getValue();
            }
            filmList.add(new FilmList(tp.getAttribute().getLabel(), value));
        }


        model.addAttribute("objects", objects);
        model.addAttribute("fl", filmList);
        return "filmEdit";
    }

    @PostMapping("/main/{id}")
    public String objGet(
            @RequestParam String objectName,
            //@ModelAttribute("fl")ArrayList<FilmList> filmL,
            @RequestParam List<String> label,
            @RequestParam List<String> value,
            @RequestParam("objectId") String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        List<Value> values = valueRep.findAllByObjects(object);
        List<TypeAttribute> typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(object.getType());
        //=============================================
        List<FilmList> filmL = new ArrayList<FilmList>();
        for(int i = 0; i < label.size(); i++) {
            filmL.add(new FilmList(label.get(i), value.get(i)));
        }

        if(!values.isEmpty()){
            System.out.println("update values there");
            object.setName(objectName);
            for (FilmList tmp: filmL
            ) {
                Value vall = valueRep.findByAttributesAndObjects(attributeRep.findByLabel(tmp.getLabel()), object);
                vall.setValue(tmp.getValue());
                valueRep.save(vall);
            }
        }
        else {
            System.out.println("adding new values there");
            object.setName(objectName);

            for (FilmList tmp: filmL
            ) {
                values.add(new Value(object, attributeRep.findByLabel(tmp.getLabel()) , tmp.getValue()));
            }

            for (Value v: values
            ) {
                valueRep.save(v);
            }
        }
        objectsRep.save(object);


        return "redirect:/main/{id}";
    }

    @GetMapping("/main/{id}/addFavorite")
    public String addFav(
            @PathVariable("id") Integer id
    ) {
        if(favoritesRep.findByUserAndObject(
                userRep.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                objectsRep.findById(id)) == null)
        {
            favoritesRep.save(new Favorites(
                    userRep.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                    objectsRep.findById(id)));
        }
        System.out.println("ADDED");
        return "redirect:/main/{id}";
    }

    @GetMapping("/main/{id}/removeFavorite")
    public String remFav(
            @PathVariable("id") Integer id
    ) {
    if (favoritesRep.findByUserAndObject(
            userRep.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
            objectsRep.findById(id))
            != null)
    {
        favoritesRep.delete(favoritesRep.findByUserAndObject(
                userRep.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                objectsRep.findById(id))
        );
    }
        System.out.println("REMOVED");
        return "redirect:/main/{id}";
    }

    @GetMapping("/main/{id}")
    public String editObj(
            Model model,
            @PathVariable String id
    ) {
        Objects object = objectsRep.findById(Integer.valueOf(id));
        List<Value> values = valueRep.findAllByObjects(object);

        //=====================================================================

        Boolean checkUser = false;
        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null) {
            Authentication authentication = context.getAuthentication();
            if(!(authentication instanceof AnonymousAuthenticationToken)) {
                User user = (User) authentication.getPrincipal();
                String role = user.getRole().toString();

                // user & favorite film

                model.addAttribute("role", role);
                checkUser = true;
                model.addAttribute("checkUser", checkUser);

                Boolean checkFilm = false;
                if(favoritesRep.findByUserAndObject(user , object)!=null) {
                    checkFilm = true;
                }
                model.addAttribute("checkFilm", checkFilm);
            }
        }

        //=====================================================================

        List<TypeAttribute> typeAttributes = typeAttributeRep.findByTypeOrderByAttribute(object.getType());
        List<FilmList> filmList = new ArrayList<FilmList>();
        for (TypeAttribute tp: typeAttributes
        ) {
            String value;
            if(valueRep.findByAttributesAndObjects(tp.getAttribute(), object) == null) {
                value = " ";
            }
            else {
                value = valueRep.findByAttributesAndObjects(tp.getAttribute(), object).getValue();
            }
            filmList.add(new FilmList(tp.getAttribute().getLabel(), value));
        }

        //=====================================================================



        //=====================================================================
        model.addAttribute("objects", object);
        model.addAttribute("fl", filmList);
        return "filmList";
    }


    @GetMapping("/editAttribute/delete/{label}")
    public String deleteAtr(
            @PathVariable("label") String label
    ) {
        if(typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findById(1)) != null
                || typeAttributeRep.findByAttributeAndType(attributeRep.findByLabel(label), typeRep.findById(2)) != null) {
            typeAttributeRep.removeByAttribute(attributeRep.findByLabel(label));
        }
        attributeRep.removeByLabel(label);
        return "redirect:/editAttribute";
    }


    // deleting attribute of type aat main attribute page
    @GetMapping("/main/{id}/deleteTypeAtt/{label}")
    public String deleteTypeAtt(
            @PathVariable("id") Integer id,
            @PathVariable("label") String label
    ) {
        typeAttributeRep.removeByAttribute(attributeRep.findByLabel(label));
        System.out.println("deleted");
        return "redirect:/main/{id}/editObjectAttributes";
    }

}
