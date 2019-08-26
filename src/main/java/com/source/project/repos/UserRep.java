package com.source.project.repos;

import com.source.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRep extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
