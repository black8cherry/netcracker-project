package com.source.project.controllers;

import com.source.project.repos.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userList")
public class UserController {

    @Autowired
    private UserRep userRep;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRep.findAll());
        return "userList";
    }
}
