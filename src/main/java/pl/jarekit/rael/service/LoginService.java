package pl.jarekit.rael.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.model.User;

@Service
public class LoginService {

    public User getUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
