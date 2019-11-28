package com.source.project.controllers;

import com.source.project.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Transactional
@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping("rate/{objectId}/{userId}")
    public String rate(
            @PathVariable("objectId") Integer objectId,
            @PathVariable("userId") String userId,
            @RequestParam(required=false) Float rating
    ) {
        try {
            if(rating!=null) {
                ratingService.rate(userId, objectId, rating);
            }
            return "redirect:/main/{objectId}";
        } catch (Exception e) {
            return "errorPage";
        }
    }
}
