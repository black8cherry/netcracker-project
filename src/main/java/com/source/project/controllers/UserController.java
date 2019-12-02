package com.source.project.controllers;

import com.source.project.domain.ObjEntity;
import com.source.project.service.FavoriteService;
import com.source.project.service.UserService;
import com.source.project.service.ValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private ValueService valueService;

    @GetMapping("/user/{id}")
    public String userPage(
            @PathVariable("id") String id,
            Model model
    ) {
        List<ObjEntity> favoriteList = favoriteService.get(id);
        model.addAttribute("fav", favoriteList);
        model.addAttribute("listImages", valueService.getMainImages(favoriteList));
        model.addAttribute("user", userService.getUser());
        return "userList";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
}
