package com.source.project.repos;

import com.source.project.domain.Objects;
import com.source.project.domain.Rating;
import com.source.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRep extends
        JpaRepository<Rating, Long>
{
    List<Rating> findByObjects(Objects objects);
    Rating findByObjectsAndUser(Objects objects, User user);
    Float countByObjects(Objects objects);
    void removeByUserAndObjects(User user, Objects objects);
}
