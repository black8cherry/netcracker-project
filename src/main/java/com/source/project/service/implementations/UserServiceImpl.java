package com.source.project.service.implementations;

import com.source.project.domain.User;
import com.source.project.repos.UserRep;
import com.source.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<User> findAllById(List<Integer> id) {
        return userRep.findAllById(id);
    }

    @Override
    public User getUser() {
        return userRep.findByUsername(SecurityContextHolder.
                getContext().
                getAuthentication().
                getName());
    }
}

