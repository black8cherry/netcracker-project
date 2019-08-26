package com.source.project.controllers;

import com.source.project.domain.Role;
import com.source.project.domain.User;
import com.source.project.repos.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRep userRep;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String adduser(User user, Map<String, Object> model) {

        User userDB = userRep.findByUsername(user.getUsername());

        if(userDB!=null)
            model.put("message","user exists");
        else {
            user.setRole(Collections.singleton(Role.ADMIN));
            userRep.save(user);
            return "redirect:/login";
        }
        return "registration";
    }
}
