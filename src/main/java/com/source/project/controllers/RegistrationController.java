package com.source.project.controllers;

import com.source.project.domain.Role;
import com.source.project.domain.User;
import com.source.project.repos.UserRep;
import com.source.project.service.FavoriteService;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String emptyPage() {return "redirect:/main";}

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ValidationController.getError(bindingResult);
            System.out.println("ERROR:" + errorMap);
            model.addAttribute("textError", errorMap);
            return "registration";
        } else {
            if (userService.registration(user)) {
                return "redirect:/login";
            } else {
                model.addAttribute("message", "Such user already exist.");
                return "registration";
            }
        }
    }


}
