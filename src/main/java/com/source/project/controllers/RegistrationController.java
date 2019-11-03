package com.source.project.controllers;

import com.source.project.domain.Role;
import com.source.project.domain.User;
import com.source.project.repos.UserRep;
import com.source.project.service.FavoriteService;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String startPage() {return "redirect:/main";}

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {

        if (userService.registration(user)) {
            return "redirect:/login";
        } else {
            model.addAttribute("message", "Such user already exist.");
            return "registration";
        }
    }
}
