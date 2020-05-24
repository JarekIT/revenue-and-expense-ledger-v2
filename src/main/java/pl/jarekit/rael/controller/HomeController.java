package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.service.IpService;

import java.security.Principal;
import java.util.Collection;

@Controller
public class HomeController {

    private IpService ipService;

    @Autowired
    public HomeController(IpService homeService) {
        this.ipService = homeService;
    }

    @GetMapping("/home")
    public String get(Principal principal, Model model){
        model.addAttribute("name",principal.getName());

        Collection<? extends GrantedAuthority> role = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        model.addAttribute("role",role);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("id",user.getId());
        model.addAttribute("password",user.getPassword());
        model.addAttribute("client",user.getClientUser());

        model.addAttribute("expireDate",user.getExpireDate());

        model.addAttribute("remoteAddress", ipService.getRemoteAddress());

        return "home";
    }

}
