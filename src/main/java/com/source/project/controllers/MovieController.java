package com.source.project.controllers;

import com.source.project.domain.ObjEntity;
import com.source.project.domain.Type;
import com.source.project.domain.User;
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

import java.util.List;
import java.util.Map;

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
    @Autowired
    private ValueService valueService;

    @GetMapping("/main/{id}")
    public String moviePage(
            Model model,
            @PathVariable Integer id
    ) {
        if (!objEntityService.checkObjectForPage(id))
            return "errorPage";
        try {
            ObjEntity objEntity = objEntityService.findById(id);

            User userAccount = userService.getUser();
            Boolean checkUser = false;
            if (userAccount != null) {
                String role = userAccount.getRole().toString();
                checkUser = true;

                model.addAttribute("checkRate", ratingService.check(id, String.valueOf(userAccount.getId())));

                model.addAttribute("userAccount", userAccount);

                model.addAttribute("role", role);

                model.addAttribute("checkFilm", favoriteService.check(String.valueOf(userAccount.getId()), String.valueOf(id)));
            }

            model.addAttribute("movieAttributes", objEntityService.showAttributes(objEntity));

            model.addAttribute( "tmpMovieAttributes", attributeService.findByObjectEntityType(objEntity.getType()));

            model.addAttribute( "Image", valueService.getMainImage(objEntity));

            model.addAttribute("attributesMessageType", attributeService.findByObjectEntityType(typeService.findById(Constants.MESSAGE_TYPE_ID)));

            model.addAttribute("userMessages", messageService.getListMessages(id));

            model.addAttribute("rate", ratingService.getRate(id));

            model.addAttribute("checkRatingType", typeService.findById(Constants.RATING_TYPE_ID) != null);

            model.addAttribute("checkFavoriteType", typeService.findById(Constants.FAVORITE_TYPE_ID) != null);

            model.addAttribute("checkMessageType", typeService.findById(Constants.MESSAGE_TYPE_ID) != null);

            model.addAttribute("checkUser", checkUser);

            model.addAttribute("movie", objEntity);

            return "filmList";
        } catch (Exception e) {
            return "errorPage";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/main/{id}/edit")
    public String editMoviePage(
            @PathVariable Integer id,
            Model model
    ) {
        if (!objEntityService.checkObjectForPage(id))
            return "errorPage";

        try {
        ObjEntity objEntity = objEntityService.findById(id);
        Map<String, String> tmpMap = objEntityService.showAttributes(objEntity);
        model.addAttribute("objects", objEntity);
        model.addAttribute("attributeValue", tmpMap);
        model.addAttribute("objectAttributes", attributeService.getListForRefactorAttributeValues(tmpMap));
        return "filmEdit";
        } catch (Exception e) {
            return "errorPage";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/main/{id}/edit")
    public String editMovie(
            @RequestParam String objectName,
            @RequestParam(required = false) List<String> label,
            @RequestParam(required = false) List<String> value,
            @RequestParam(required = false) List<MultipartFile> file,
            @RequestParam("objectId") Integer id
    ) {
        if (!objEntityService.checkObjectForPage(id))
            return "errorPage";
        try {
        if (label!=null)
            objEntityService.edit(objectName, label, value, id, file, uploadPath);
        return "redirect:/main/{id}";
        } catch (Exception e) {
            return "errorPage";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addFilm")
    public String addMoviePage(
            Model model
    ) {
        List<Type> typeList = typeService.findTreeFromParent(1);
        List<ObjEntity> movies = objEntityService.getObjEntitiesByTypeInOrderByName(typeList);
        model.addAttribute("listImages", valueService.getMainImages(movies));
        model.addAttribute("types", typeService.findTreeFromParent(Constants.VIDEO_OBJECT_TYPE_ID));
        model.addAttribute("movie", movies);
        return "addFilm";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addFilm")
    public String addingMovie(
            @RequestParam String name,
            @RequestParam Integer typeId
    ) {
        if (!name.isEmpty() && typeId != null) {
            Integer id = objEntityService.save(name, typeId);
            return "redirect:/main/"+id+"/edit";
        }
        return "redirect:/addFilm";
    }

}
