package com.source.project.controllers;

import com.source.project.domain.Objects;
import com.source.project.domain.User;
import com.source.project.repos.*;
import com.source.project.service.ObjectsService;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Transactional
@Controller
@RequestMapping(value = {"/main"})
public class MainController {

    @Autowired
    private ObjectsService objectsService;
    @Autowired
    private UserService userService;

    @GetMapping("/administratorPanel")
    public String administratorPanel() {

        return "administratorPanel";
    }

    @GetMapping
    public String main(
            @RequestParam(required=false) String filter,
            Model model
    ) {
        Iterable<Objects> objects = objectsService.findAll();

        if (filter != null && !filter.isEmpty()) {
            objects = objectsService.findByNameOrderByName(filter);
        } else {
            objects = objectsService.findAll(Sort.by("name"));
        }

        Boolean checkUser = false;
        //============================================================================
        SecurityContext context = SecurityContextHolder.getContext();
        if(context != null) {
            Authentication authentication = context.getAuthentication();
            if(!(authentication instanceof AnonymousAuthenticationToken)) {
                User user = (User) authentication.getPrincipal();
                String role = user.getRole().toString();
                checkUser = true;
                //System.out.println("role = " + role);
                model.addAttribute("role", role);
                User userAcc = userService.findByUsername(authentication.getName());
                model.addAttribute("userAcc", userAcc);
            }
        }
        //=============================================================================


        model.addAttribute("checkUser", checkUser);
        model.addAttribute("objects", objects);
        model.addAttribute("filter", filter);
        return "main";
    }

    @GetMapping("/delete/{id}")
    public String deleteObj(
            @PathVariable("id") Integer id
    ) {
        objectsService.removeById(id);
        return "redirect:/main";
    }

    @GetMapping("/test")
    public String test()
    {
        return "test";
    }
}
