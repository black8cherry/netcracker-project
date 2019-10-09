package com.source.project.repos;

import com.source.project.domain.Favorites;
import com.source.project.domain.Objects;
import com.source.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRep extends
        CrudRepository<Favorites, Long>,
        JpaRepository<Favorites, Long>
{
    Favorites findByUserAndObject(User user, Objects objects);
    List<Favorites> findByUser(User user);
}