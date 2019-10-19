package com.source.project.repos;

import com.source.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRep extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findById(Integer id);
    List<User> findAllById(List<Integer> id);
}
