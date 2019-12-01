package com.source.project.controllers;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.User;
import com.source.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Controller
@RequestMapping(value = {"/main"})
public class MainPageController {

    @Autowired
    private ObjEntityService objEntityService;
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private ValueService valueService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/administratorPanel")
    public String administratorPanel() {
        return "administratorPanel";
    }

    @GetMapping
    public String mainPage(
            @RequestParam(required=false) String filter,
            Model model
    ) {
        List<Type> typeList = typeService.findTreeFromParent(Constants.VIDEO_OBJECT_TYPE_ID);
        List<ObjEntity> movies;

        if (filter != null && !filter.isEmpty()) {
            movies = objEntityService.findByNameIsContaining(filter);
        } else {
            movies = objEntityService.getObjEntitiesByTypeInOrderByName(typeList);
        }

        User userAcc = userService.getUser();
        Boolean checkUser = false;
        if(userAcc != null) {

            String role = userAcc.getRole().toString();
            checkUser = true;
            model.addAttribute("userAcc", userAcc);
            model.addAttribute("role", role);
        }
        model.addAttribute("listImages", valueService.getMainImages(movies));
        model.addAttribute("checkUser", checkUser);
        model.addAttribute("movie", movies);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/delete/{id}")
    public String deleteObj(
            @PathVariable("id") Integer id
    ) {
        try {
            objEntityService.removeById(id);
            return "redirect:/main";
        } catch (Exception e) {
            return "errorPage";
        }
    }

}
