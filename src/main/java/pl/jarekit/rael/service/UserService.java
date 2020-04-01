package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.LogUtils;
import pl.jarekit.rael.model.Client;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.repo.ClientRepo;
import pl.jarekit.rael.repo.UserRepo;

@Service
public class UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private ClientRepo clientRepo;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, ClientRepo clientRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.clientRepo = clientRepo;
    }

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        LogUtils.saveLogStatic("Added user role: " + user , Level.INFO);
        userRepo.save(user);
    }

    public void addAdmin(User admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("ROLE_ADMIN");
        LogUtils.saveLogStatic("Added admin role: " + admin , Level.WARNING);
        userRepo.save(admin);
    }

    public User setClientInUser(User user, Long id){
        User userToEdit = user;
        Client client = clientRepo.findById(id).get();
        userToEdit.setClientUser(client);
        return userRepo.save(userToEdit);
    }

    public Iterable<User> getUsersForAdmin() {
        LogUtils.saveLogStatic("Loaded all users for admin panel" , Level.WARNING);
        return userRepo.findAll();
    }
}
