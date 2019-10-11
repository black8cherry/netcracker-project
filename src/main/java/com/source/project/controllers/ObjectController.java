package com.source.project.controllers;


import com.source.project.domain.*;
import com.source.project.service.*;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class ObjectController {

    @Autowired
    private TypeAttributeService typeAttributeService;
    @Autowired
    private ValueService valueService;
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private ObjectsService objectsService;
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeRService;
    @Autowired
    private FavoritesService favoritesService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RatingService ratingService;

    @GetMapping("/main/{id}")
    public String ObjGet(
            Model model,
            @PathVariable Integer id
    ) {
        Objects object = objectsService.findById(id);
        List<Value> values = valueService.findAllByObjects(object);

        //=========== Getting User ==========================================

        Boolean checkUser = false;
        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null) {
            Authentication authentication = context.getAuthentication();
            if(!(authentication instanceof AnonymousAuthenticationToken)) {

                User user = (User) authentication.getPrincipal();
                String role = user.getRole().toString();
                User userAcc = userService.findByUsername(authentication.getName());

                checkUser = true;

                boolean checkRate = false;
                if(ratingService.findByObjectsAndUser(object, userAcc)!=null) {
                    checkRate = true;
                }

                model.addAttribute("checkRate", checkRate);
                model.addAttribute("userAcc", userAcc);
                model.addAttribute("role", role);

                Boolean checkFilm = false;
                if(favoritesService.findByUserAndObject(user , object)!=null) {
                    checkFilm = true;
                }
                model.addAttribute("checkFilm", checkFilm);
            }
        }

        //========== Attribute List =========================================

        List<TypeAttribute> typeAttributes = typeAttributeService.findByTypeOrderByAttribute(object.getType());
        List<FilmList> filmList = new ArrayList<FilmList>();
        for (TypeAttribute tp: typeAttributes
        ) {
            String value;
            if(valueService.findByAttributesAndObjects(tp.getAttribute(), object) == null) {
                value = " ";
            }
            else {
                value = valueService.findByAttributesAndObjects(tp.getAttribute(), object).getValue();
            }
            filmList.add(new FilmList(tp.getAttribute().getLabel(), value));
        }

        //=====================================================================

        List<Message> messages = messageService.findByObjects(objectsService.findById(id));
        model.addAttribute("messages", messages);

        //=====================================================================

        float result = 0;
        List<Rating> ratingList = ratingService.findByObjects(object);
        for (Rating rat: ratingList
             ) {
            result += rat.getRate();
        }

        String res = "no one has rated this movie yet";

        if(result != 0) {
            res = new DecimalFormat("#0.00")
                    .format(result / ratingService.countByObjects(object));
        }

        //=====================================================================

        model.addAttribute("rate", res);
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
            messageService.save(new Message(
                    userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                    objectsService.findById(id),
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
        Objects object = objectsService.findById(Integer.valueOf(id));
        List<Value> values = valueService.findAllByObjects(object);
        List<TypeAttribute> typeAttributes = typeAttributeService.findByTypeOrderByAttribute(object.getType());
        //=============================================
        List<FilmList> filmL = new ArrayList<FilmList>();
        for(int i = 0; i < label.size(); i++) {
            filmL.add(new FilmList(label.get(i), value.get(i)));
        }

        if(!values.isEmpty()){
            object.setName(objectName);
            for (FilmList tmp: filmL
            ) {
                Value vall = valueService.findByAttributesAndObjects(
                        attributeService.findByLabel(tmp.getLabel()),
                        object);
                vall.setValue(tmp.getValue());
                valueService.save(vall);
            }
        }
        else {
            object.setName(objectName);
            for (FilmList tmp: filmL
            ) {
                values.add(new Value(object, attributeService.findByLabel(tmp.getLabel()) , tmp.getValue()));
            }
            for (Value v: values
            ) {
                valueService.save(v);
            }
        }
        objectsService.save(object);


        return "redirect:/main/{id}";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/main/{id}/edit")
    public String ObjEditGet(
            @PathVariable String id,
            Model model
    ) {
        Objects objects = objectsService.findById(Integer.valueOf(id));
        //List<Value> values = valueRep.findAllByObjects(objects);
        List<TypeAttribute> typeAttributes = typeAttributeService.findByTypeOrderByAttribute(objects.getType());
        List<FilmList> filmList = new ArrayList<FilmList>();

        for (TypeAttribute tp: typeAttributes
        ) {
            String value;
            if(valueService.findByAttributesAndObjects(tp.getAttribute(), objects) == null) {
                value = " ";
            }
            else {
                value = valueService.findByAttributesAndObjects(tp.getAttribute(), objects).getValue();
            }
            filmList.add(new FilmList(tp.getAttribute().getLabel(), value));
        }


        model.addAttribute("objects", objects);
        model.addAttribute("fl", filmList);
        return "filmEdit";
    }

    // Favorites

    @GetMapping("/main/{id}/addFavorite")
    public String addFav(
            @PathVariable("id") Integer id
    ) {
        if(favoritesService.findByUserAndObject(
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                objectsService.findById(id)) == null)
        {
            favoritesService.save(new Favorites(
                    userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                    objectsService.findById(id)));
        }
        return "redirect:/main/{id}";
    }

    @GetMapping("/main/{id}/removeFavorite")
    public String remFav(
            @PathVariable("id") Integer id
    ) {
    if (favoritesService.findByUserAndObject(
            userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
            objectsService.findById(id))
            != null)
    {
        favoritesService.delete(favoritesService.findByUserAndObject(
                userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),
                objectsService.findById(id))
        );
    }
        return "redirect:/main/{id}";
    }

    // Attributes

    @GetMapping("/deleteMessage/{id}/{idm}")
    public String delMes(
            @PathVariable("idm") Integer idm,
            @PathVariable("id") Integer id
    ) {
        messageService.removeById(idm);
        return"redirect:/main/{id}";
    }

    // Rating

    @GetMapping("rate/{ido}/{idu}")
    public String rate(
            @PathVariable("ido") Integer ido,
            @PathVariable("idu") Integer idu,
            @RequestParam(required=false) Float rating
    ) {
        if(rating!=null) {
            if (ratingService.findByObjectsAndUser(
                    objectsService.findById(ido),
                    userService.findById(idu)) != null)
            {
                ratingService.rerate(userService.findById(idu),
                        objectsService.findById(ido),
                        rating);
            } else {
                ratingService.save(userService.findById(idu),
                        objectsService.findById(ido),
                        rating);
            }
        }
        return "redirect:/main/{ido}";
    }



}
