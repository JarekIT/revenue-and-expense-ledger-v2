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
    private SubscriptionService subscriptionService;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo, SubscriptionService subscriptionService) {
        this.userRepo = userRepo;
        this.subscriptionService = subscriptionService;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> loadedUser = userRepo.findByUsername(s);
        if (loadedUser.isPresent()){
            subscriptionService.giveFree7DaysSubscriptionIfFirstTimeLogin(loadedUser.get());
            return loadedUser.get();
        } else {
            throw new UsernameNotFoundException("Not found username named: " + s);
        }
    }

}
