package com.source.project.service.implementations;

import com.source.project.domain.Favorites;
import com.source.project.domain.Objects;
import com.source.project.domain.User;
import com.source.project.repos.FavoritesRep;
import com.source.project.service.FavoritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritesServiceImpl implements FavoritesService {

    @Autowired
    private FavoritesRep favoritesRep;

    @Override
    public Favorites findByUserAndObject(User user, Objects objects) {
        return favoritesRep.findByUserAndObject(user, objects);
    }

    @Override
    public List<Favorites> findByUser(User user) {
        return favoritesRep.findByUser(user);
    }

    @Override
    public void save(Favorites favorites) {
        favoritesRep.save(favorites);
    }

    @Override
    public void delete(Favorites favorites) {
        favoritesRep.delete(favorites);
    }
}
