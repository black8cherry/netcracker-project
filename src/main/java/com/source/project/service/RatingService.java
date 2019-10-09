package com.source.project.service;

import com.source.project.domain.Objects;
import com.source.project.domain.Rating;
import com.source.project.domain.User;

import java.util.List;

public interface RatingService {
    List<Rating> findByObjects(Objects objects);
    Float countByObjects(Objects objects);
    void save( User user, Objects objects, Float rate);
    void rerate(User user, Objects objects, Float rate);
    Rating findByObjectsAndUser(Objects objects, User user);
}
