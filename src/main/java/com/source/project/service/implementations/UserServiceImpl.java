package com.source.project.service.implementations;

import com.source.project.domain.Role;
import com.source.project.domain.User;
import com.source.project.repos.UserRep;
import com.source.project.service.FavoriteService;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private UserRep userRep;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRep.findByUsername(username);
    }

    @Override
    public boolean registration(User user) {

        User userDB = userRep.findByUsername(user.getUsername());

        if (userDB != null)
            return false;
        else {
            user.setRole(Collections.singleton(Role.USER));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRep.save(user);
            favoriteService.create(String.valueOf(user.getId()));
            return true;
        }
    }

    @Override
    public User findById(Integer id) {
        return userRep.findById(id);
    }

    @Override
    public void save(User user) {
        userRep.save(user);
    }

    @Override
    public User getUser() {
        return userRep.findByUsername(SecurityContextHolder.
                getContext().
                getAuthentication().
                getName());
    }
}

