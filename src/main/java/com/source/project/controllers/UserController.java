package com.source.project.controllers;

import com.source.project.domain.User;
import com.source.project.service.FavoriteService;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/user/{id}")
    public String userPage(
            @PathVariable("id") String id,
            Model model
    ) {
        model.addAttribute("fav", favoriteService.get(id));
        model.addAttribute("user", userService.getUser());
        return "userList";
    }
}
