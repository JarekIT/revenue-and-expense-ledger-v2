package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.LogUtils;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.repo.UserRepo;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepo userRepo;
    private SubscriptionService subscriptionService;
    private IpService ipService;

    @Autowired
    public UserDetailsServiceImpl(UserRepo userRepo, SubscriptionService subscriptionService, IpService userService) {
        this.userRepo = userRepo;
        this.subscriptionService = subscriptionService;
        this.ipService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> loadedUser = userRepo.findByUsername(s);
        if (loadedUser.isPresent()){
            subscriptionService.giveFree7DaysSubscriptionIfFirstTimeLogin(loadedUser.get());

            LogUtils.saveLogStatic(String.format("Client log in:  %s, from ip: %s",
                    loadedUser.get().getUsername(),
                    ipService.getRemoteAddress()),
                    Level.INFO);

            return loadedUser.get();
        } else {
            throw new UsernameNotFoundException("Not found username named: " + s);
        }
    }

}
