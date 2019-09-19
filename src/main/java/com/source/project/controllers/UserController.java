package com.source.project.controllers;

import com.source.project.domain.Favorites;
import com.source.project.domain.User;
import com.source.project.repos.FavoritesRep;
import com.source.project.repos.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRep userRep;
    @Autowired
    private FavoritesRep favoritesRep;


    @GetMapping("/user/{id}")
    public String userList(
            @PathVariable("id") Long id,
            Model model
    ) {

        User user = userRep.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(user.getUsername());
        List<Favorites> favorites = favoritesRep.findByUser(user);

        model.addAttribute("fav", favorites);
        model.addAttribute("user", user);
        return "userList";
    }
}
