package com.source.project.controllers;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.User;
import com.source.project.domain.resources.FilmListConnector;
import com.source.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class MovieController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private ObjEntityService objEntityService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private AttributeService attributeService;

    @GetMapping("/main/{id}")
    public String moviePage(
            Model model,
            @PathVariable Integer id
    ) {
        ObjEntity objEntity = objEntityService.findById(id);

        User userAccount = userService.getUser();
        Boolean checkUser = false;
        if(userAccount != null) {
            String role = userAccount.getRole().toString();
            checkUser = true;

            model.addAttribute("checkRate", ratingService.findByObjectsAndUser(id, String.valueOf(userAccount.getId())));

            model.addAttribute("userAccount", userAccount);

            model.addAttribute("role", role);

            model.addAttribute("checkFilm", favoriteService.check(String.valueOf(userAccount.getId()), String.valueOf(id)));
        }

        model.addAttribute("movieAttributes", objEntityService.showAttributes(objEntity));

        model.addAttribute("attributesMessageType", attributeService.findByObjectEntityType(typeService.findById(3)));

        model.addAttribute("userMessages", messageService.getListMessages(id));

        model.addAttribute("rate", ratingService.getRate(id));

        model.addAttribute("checkUser", checkUser);

        model.addAttribute("movie", objEntity);

        return "filmList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/main/{id}/edit")
    public String editMoviePage(
            @PathVariable String id,
            Model model
    ) {
        ObjEntity objEntity = objEntityService.findById(Integer.valueOf(id));
        List<FilmListConnector> filmListConnector = objEntityService.showAttributes(objEntity);

        model.addAttribute("objects", objEntity);

        model.addAttribute("fl", filmListConnector);
        return "filmEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/main/{id}/edit")
    public String editMovie(
            @RequestParam String objectName,
            @RequestParam List<String> label,
            @RequestParam List<String> value,
            @RequestParam("objectId") Integer id
    ) {
        objEntityService.edit(objectName, label, value, id);
        return "redirect:/main/{id}";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addFilm")
    public String addMoviePage(
            Model model
    ) {
        List<Type> typeList = typeService.findTreeFromParent(1);
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("movie", objEntityService.getObjEntitiesByTypeInOrderByName(typeList));
        return "addFilm";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addFilm")
    public String addingMovie(
            @RequestParam String name,
            @RequestParam Integer typeId,
            @RequestParam MultipartFile file
    ) throws IOException {

        if (name != null && typeId != null) {
            objEntityService.save(name, typeId, file, uploadPath);
        }
        return "redirect:/addFilm";
    }


}
