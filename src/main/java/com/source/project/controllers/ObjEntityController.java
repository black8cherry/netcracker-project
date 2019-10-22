package com.source.project.controllers;


import com.source.project.domain.*;
import com.source.project.domain.ObjEntity;
import com.source.project.domain.resources.FilmList;
import com.source.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class ObjEntityController { // entity!

    @Autowired
    private MessageService messageService;
    @Autowired
    private TypeAttributeService typeAttributeService;
    @Autowired
    private ValueService valueService;
    @Autowired
    private AttributeService attributeService;
    @Autowired
    private ObjEntityService objEntityService;
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/main/{id}")
    public String ObjGet(
            Model model,
            @PathVariable Integer id
    ) {
        ObjEntity objEntity = objEntityService.findById(id);

        //=========== Getting User ==========================================

        User userAcc = userService.getUser();
        Boolean checkUser = false;
        if(userAcc != null) {
            String role = userAcc.getRole().toString();
            checkUser = true;

            model.addAttribute("checkRate", ratingService.findByObjectsAndUser(id, String.valueOf(userAcc.getId())));
            model.addAttribute("userAcc", userAcc);
            model.addAttribute("role", role);
            model.addAttribute("checkFilm", favoriteService.check(String.valueOf(userAcc.getId()), String.valueOf(id)));
        }

        //========== Attribute List =========================================

        List<TypeAttribute> typeAttributes = typeAttributeService.findByTypeOrderByAttribute(objEntity.getType());
        List<FilmList> filmList = new ArrayList<FilmList>();
        for (TypeAttribute tp: typeAttributes
        ) {
            String value;
            if(valueService.findByAttributesAndObjects(tp.getAttribute(), objEntity) == null) {
                value = " ";
            }
            else {
                value = valueService.findByAttributesAndObjects(tp.getAttribute(), objEntity).getValue();
            }
            filmList.add(new FilmList(tp.getAttribute().getLabel(), value));
        }

        //============ Messages ========================================

        model.addAttribute("usrMes", messageService.getListMes(id));

        //============== Rating ===========================================

        model.addAttribute("rate", ratingService.getRate(id));

        //=====================================================================

        model.addAttribute("checkUser", checkUser);
        model.addAttribute("movie", objEntity);
        model.addAttribute("fl", filmList);
        return "filmList";
    }

    @PostMapping("/main/{id}")
    public String ObjPost(
        @RequestParam String message,
        @RequestParam String userId,
        @PathVariable("id") Integer id
    ) {
        messageService.save(userId , id, message);
        return "redirect:/main/{id}";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/main/{id}/edit")
    public String ObjEditPost(
            @RequestParam String objectName,
            @RequestParam List<String> label,
            @RequestParam List<String> value,
            @RequestParam("objectId") String id
    ) {
        objEntityService.edit(objectName, label, value, id);
        return "redirect:/main/{id}";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/main/{id}/edit")
    public String ObjEditGet(
            @PathVariable String id,
            Model model
    ) {
        ObjEntity objEntity = objEntityService.findById(Integer.valueOf(id));

        List<FilmList> filmList = objEntityService.showAttributes(objEntity);

        model.addAttribute("objects", objEntity);
        model.addAttribute("fl", filmList);
        return "filmEdit";
    }

    // Favorites id name:uid ido parentid

    @GetMapping("/main/{id}/{uid}/addFavorite")
    public String addFav(
            @PathVariable("id") String objectId,
            @PathVariable("uid") String userId
    ) {
        favoriteService.save(userId, objectId);
        return "redirect:/main/{id}";
    }

    @GetMapping("/main/{id}/{uid}/removeFavorite")
    public String remFav(
            @PathVariable("id") String objectId,
            @PathVariable("uid") String userId
    ) {
        favoriteService.delete(userId, objectId);
        return "redirect:/main/{id}";
    }

    // Attributes

    @GetMapping("/deleteMessage/{id}/{ido}")
    public String delMes(
            @PathVariable("ido") Integer ido,
            @PathVariable("id") Integer id
    ) {
        messageService.delete(ido);
        return"redirect:/main/{id}";
    }

    // Rating

    @GetMapping("rate/{ido}/{idu}")
    public String rate(
            @PathVariable("ido") Integer ido,
            @PathVariable("idu") String idu,
            @RequestParam(required=false) Float rating
    ) {
        if(rating!=null) {
            if(!ratingService.findByObjectsAndUser(ido, idu)) {
                ratingService.save(idu, ido, rating);
            } else {
                ratingService.rerate(idu, ido, rating);
            }
        }
        return "redirect:/main/{ido}";
    }



}
