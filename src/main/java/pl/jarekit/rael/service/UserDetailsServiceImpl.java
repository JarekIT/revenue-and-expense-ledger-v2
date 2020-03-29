package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.repo.UserRepo;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepo userRepo;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // todo throw if not exist
        Optional<User> byUsername = userRepo.findByUsername(s);
        if (!byUsername.isPresent()){
            throw new UsernameNotFoundException("Not found username named: " + s);
        } else {
            return byUsername.get();
        }
    }


    public void setClientToUser(){

    }
}
