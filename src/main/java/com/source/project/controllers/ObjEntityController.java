package com.source.project.controllers;


import com.source.project.domain.ObjEntity;
import com.source.project.domain.User;
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
import java.util.List;

@Transactional
@Controller
public class ObjEntityController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ObjEntityService objEntityService;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/main/{id}")
    public String moviePage(
            Model model,
            @PathVariable Integer id
    ) {
        ObjEntity objEntity = objEntityService.findById(id);

        User userAccount = userService.getUser();
        Boolean checkUser = false;
        if(userAccount != null) {
            String role = userAccount.getRole().toString();
            checkUser = true;

            model.addAttribute("checkRate", ratingService.findByObjectsAndUser(id, String.valueOf(userAccount.getId())));

            model.addAttribute("userAccount", userAccount);

            model.addAttribute("role", role);

            model.addAttribute("checkFilm", favoriteService.check(String.valueOf(userAccount.getId()), String.valueOf(id)));
        }

        model.addAttribute("movieAttributes", objEntityService.showAttributes(objEntity));

        model.addAttribute("userMessages", messageService.getListMessages(id));

        model.addAttribute("rate", ratingService.getRate(id));

        model.addAttribute("checkUser", checkUser);

        model.addAttribute("movie", objEntity);

        return "filmList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/main/{id}/edit")
    public String editMovie(
            @RequestParam String objectName,
            @RequestParam List<String> label,
            @RequestParam List<String> value,
            @RequestParam("objectId") Integer id
    ) {
        objEntityService.edit(objectName, label, value, id);
        return "redirect:/main/{id}";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/main/{id}/edit")
    public String editMoviePage(
            @PathVariable String id,
            Model model
    ) {
        ObjEntity objEntity = objEntityService.findById(Integer.valueOf(id));
        List<FilmList> filmList = objEntityService.showAttributes(objEntity);

        model.addAttribute("objects", objEntity);

        model.addAttribute("fl", filmList);
        return "filmEdit";
    }

}
