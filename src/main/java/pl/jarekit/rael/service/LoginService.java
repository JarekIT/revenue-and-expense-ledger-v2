package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.model.User;

@Service
public class LoginService {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public LoginService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public User getUser(){

        Object userObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userObj.getClass().equals(User.class)){
            return (User) userObj;
        } else {
            return (User) userDetailsService.loadUserByUsername(userObj.toString());
        }
    }
}
