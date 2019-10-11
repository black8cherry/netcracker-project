package com.source.project.service.implementations;

import com.source.project.domain.User;
import com.source.project.repos.UserRep;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

        @Autowired
        private UserRep userRep;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return userRep.findByUsername(username);
        }

    @Override
    public User findByUsername(String username) {
        return userRep.findByUsername(username);
    }

    @Override
    public User findById(Integer id) {
        return userRep.findById(id);
    }

    @Override
    public void save(User user) {
        userRep.save(user);
    }
}

