package com.source.project.controllers;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.User;
import com.source.project.service.ObjEntityService;
import com.source.project.service.TypeService;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Collection;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/administratorPanel")
    public String administratorPanel() {
        return "administratorPanel";
    }

    @GetMapping
    public String main(
            @RequestParam(required=false) String filter,
            Model model
    ) {
        List<Type> typeList = typeService.findTreeFromParent(1); // 1 - video
        List<ObjEntity> movies;

        if (filter != null && !filter.isEmpty()) {
            movies = objEntityService.findByNameIsContaining(filter);
        } else {
            movies = objEntityService.getObjEntitiesByTypeInOrderByName(typeList);
        }

        //============================================================================

        User userAcc = userService.getUser();
        Boolean checkUser = false;
        if(userAcc != null) {
            String role = userAcc.getRole().toString();
            checkUser = true;
            model.addAttribute("userAcc", userAcc);
            model.addAttribute("role", role);
        }

        //=============================================================================

        model.addAttribute("checkUser", checkUser);
        model.addAttribute("movie", movies);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteObj(
            @PathVariable("id") Integer id
    ) {
        objEntityService.removeById(id);
        return "redirect:/main";
    }

}
