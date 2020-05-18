package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.service.UserService;

@Controller
public class AppController {

    private UserService userService;

    @Autowired
    public AppController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String viewHomePage(){
        return "login";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/loginAdmin")
    public String loginAdmin(){
        return "loginAdmin";
    }

    @GetMapping("/register")
    public String signUp(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String signUp(User appUser){
        userService.addUser(appUser);
        return "register";
    }
}
