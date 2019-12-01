package com.source.project.controllers;

import com.source.project.domain.Type;
import com.source.project.service.Constants;
import com.source.project.service.ObjEntityService;
import com.source.project.service.RatingService;
import com.source.project.service.TypeService;
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
    @Autowired
    private TypeService typeService;
    @Autowired
    private ObjEntityService objEntityService;

    @RequestMapping("rate/{objectId}/{userId}")
    public String rate(
            @PathVariable("objectId") Integer objectId,
            @PathVariable("userId") String userId,
            @RequestParam(required=false) Float rating
    ) {
        if (!objEntityService.checkObjectForPage(objectId))
            return "errorPage";
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
