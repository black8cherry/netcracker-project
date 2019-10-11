package com.source.project.service.implementations;

import com.source.project.domain.Objects;
import com.source.project.domain.Rating;
import com.source.project.domain.User;
import com.source.project.repos.RatingRep;
import com.source.project.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRep ratingRep;

    @Override
    public List<Rating> findByObjects(Objects objects) {
        return ratingRep.findByObjects(objects);
    }

    @Override
    public Float countByObjects(Objects objects) {
        return ratingRep.countByObjects(objects);
    }

    @Override
    public void save(User user, Objects objects, Float rate) {
        ratingRep.save(new Rating( user, objects, rate));
    }

    @Override
    public void rerate( User user, Objects objects, Float rate) {
        ratingRep.removeByUserAndObjects(user, objects);
        ratingRep.save(new Rating(user, objects, rate));
    }

    @Override
    public Rating findByObjectsAndUser(Objects objects, User user) {
        return ratingRep.findByObjectsAndUser(objects, user);
    }
}
