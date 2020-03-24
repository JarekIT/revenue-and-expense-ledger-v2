package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.service.UserService;

import java.security.Principal;
import java.util.Collection;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/home")
    public String get(Principal principal, Model model){
        model.addAttribute("name",principal.getName());

        Collection<? extends GrantedAuthority> role = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        model.addAttribute("role",role);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("id",user.getId());
        model.addAttribute("password",user.getPassword());
        model.addAttribute("clients",user.getClients());

        return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/register")
    public String signUp(Model model){
        model.addAttribute("user",new User());
        return "register";
    }


    @PostMapping("/register")
    public String signUp(User appUser){
        System.out.println(appUser);
        userService.addUser(appUser);
        return "register";
    }



}
