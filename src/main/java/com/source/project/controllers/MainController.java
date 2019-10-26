package com.source.project.controllers;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.User;
import com.source.project.repos.TypeRep;
import com.source.project.service.ObjEntityService;
import com.source.project.service.TypeService;
import com.source.project.service.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Transactional
@Controller
@RequestMapping(value = {"/main"})
public class MainController {

    @Autowired
    private ObjEntityService objEntityService;
    @Autowired
    private UserService userService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/administratorPanel")
    public String administratorPanel() {
        return "administratorPanel";
    }

    @GetMapping
    public String main(
            @RequestParam(required=false) String filter,
            Model model
    ) {
        Collection<Type> typeCollection = typeService.findAllByParentId(1); // 1 - video
        List<ObjEntity> movies;

        if (filter != null && !filter.isEmpty()) {
            movies = objEntityService.getAllByNameIsLike(filter); // +collection
        } else {
            movies = objEntityService.getObjEntitiesByTypeInOrderByName(typeCollection);
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

        //System.out.println("types : " + typeService.findTree(6));

        model.addAttribute("checkUser", checkUser);
        model.addAttribute("movie", movies);
        model.addAttribute("filter", filter);
        return "main";
    }

    @GetMapping("/delete/{id}")
    public String deleteObj(
            @PathVariable("id") Integer id
    ) {
        objEntityService.removeById(id);
        return "redirect:/main";
    }

    @GetMapping("/test")
    public String test()
    {
        return "test";
    }
}
