package com.source.project.controllers;

import com.source.project.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @RequestMapping("rate/{ido}/{idu}")
    public String rate(
            @PathVariable("ido") Integer ido,
            @PathVariable("idu") String idu,
            @RequestParam(required=false) Float rating
    ) {
        if(rating!=null) {
            if(!ratingService.findByObjectsAndUser(ido, idu)) {
                ratingService.save(idu, ido, rating);
            } else {
                ratingService.rerate(idu, ido, rating);
            }
        }
        return "redirect:/main/{ido}";
    }
}
