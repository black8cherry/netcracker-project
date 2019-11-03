package com.source.project.controllers;

import com.source.project.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FavoritesController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/main/{id}/{uid}/addFavorite")
    public String addFavorite(
            @PathVariable("id") String objectId,
            @PathVariable("uid") String userId
    ) {
        favoriteService.save(userId, objectId);
        return "redirect:/main/{id}";
    }

    @GetMapping("/main/{id}/{uid}/removeFavorite")
    public String removeFavorite(
            @PathVariable("id") String objectId,
            @PathVariable("uid") String userId
    ) {
        favoriteService.delete(userId, objectId);
        return "redirect:/main/{id}";
    }
}
