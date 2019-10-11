package com.source.project.controllers;

import com.source.project.domain.Role;
import com.source.project.domain.User;
import com.source.project.repos.UserRep;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String adduser(User user, Map<String, Object> model) {

        User userDB = userService.findByUsername(user.getUsername());

        if(userDB!=null)
            model.put("message","user exists");
        else {
            user.setRole(Collections.singleton(Role.USER));
            userService.save(user);
            return "redirect:/login";
        }
        return "registration";
    }
}
