package com.source.project.controllers;

import antlr.debug.MessageAdapter;
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
    @Autowired
    private MessageRep messageRep;

    @GetMapping("/main/{id}")
    public String ObjGet(
            Model model,
            @PathVariable Integer id
    ) {
        Objects object = objectsRep.findById(id);
        List<Value> values = valueRep.findAllByObjects(object);

        //=========== Getting User ==========================================

        Boolean checkUser = false;
        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null) {
            Authentication authentication = context.getAuthentication();
            if(!(authentication instanceof AnonymousAuthenticationToken)) {

                User user = (User) authentication.getPrincipal();
                String role = user.getRole().toString();
                User userAcc = userRep.findByUsername(authentication.getName());

                checkUser = true;


                model.addAttribute("userAcc", userAcc);
                model.addAttribute("role", role);

                Boolean checkFilm = false;
                if(favoritesRep.findByUserAndObject(user , object)!=null) {
                    checkFilm = true;
                }
                model.addAttribute("checkFilm", checkFilm);
            }
        }

        //========== Attribute List =========================================

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

        List<Message> messages = messageRep.findByObjects(objectsRep.findById(id));
        model.addAttribute("messages", messages);

        //=====================================================================

        model.addAttribute("checkUser", checkUser);
        model.addAttribute("objects", object);
        model.addAttribute("fl", filmList);
        return "filmList";
    }

    @PostMapping("/main/{id}")
    public String ObjPost(
        Model model,
        @RequestParam String message,
        @PathVariable("id") Integer id
    ) {
        if(!message.isEmpty()) {
            messageRep.save(new Message(
                    userRep.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                    objectsRep.findById(id),
                    message
                    ));
        }
        return "redirect:/main/{id}";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/main/{id}/edit")
    public String ObjEditPost(
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
            object.setName(objectName);
            for (FilmList tmp: filmL
            ) {
                Value vall = valueRep.findByAttributesAndObjects(attributeRep.findByLabel(tmp.getLabel()), object);
                vall.setValue(tmp.getValue());
                valueRep.save(vall);
            }
        }
        else {
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/main/{id}/edit")
    public String ObjEditGet(
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
        return "redirect:/main/{id}";
    }

}
