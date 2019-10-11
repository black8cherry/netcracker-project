package com.source.project.service;

import com.source.project.domain.Favorites;
import com.source.project.domain.Objects;
import com.source.project.domain.User;

import java.util.List;

public interface FavoritesService {
    Favorites findByUserAndObject(User user, Objects objects);
    List<Favorites> findByUser(User user);
    void save(Favorites favorites);
    void delete(Favorites favorites);
}
