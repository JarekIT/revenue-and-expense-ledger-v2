package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/setup/{clientId}")
    public String setClientToLoggedUser(@PathVariable long clientId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.setClientInUser(user, clientId);
        return "redirect:/home";
    }

    @GetMapping("/changePassword/{newPassword}")
    public String changePassword(@PathVariable String newPassword){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.setPasswordInUser(user, newPassword);
        return "redirect:/home";
    }

}
