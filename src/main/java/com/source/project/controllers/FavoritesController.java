package com.source.project.controllers;

import com.source.project.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Transactional
@Controller
public class FavoritesController {

    @Autowired
    private FavoriteService favoriteService;

    @RequestMapping("/main/{id}/{uid}/addFavorite")
    public String addFavorite(
            @PathVariable("id") String objectId,
            @PathVariable("uid") String userId
    ) {
        try {
            favoriteService.save(userId, objectId);
            return "redirect:/main/{id}";
        } catch (NullPointerException e) {
            return "errorPage";
        }
    }

    @RequestMapping("/main/{id}/{uid}/removeFavorite")
    public String removeFavorite(
            @PathVariable("id") String objectId,
            @PathVariable("uid") String userId
    ) {
        try {
            favoriteService.delete(userId, objectId);
            return "redirect:/main/{id}";
        } catch (NullPointerException e) {
            return "errorPage";
        }
    }
}
