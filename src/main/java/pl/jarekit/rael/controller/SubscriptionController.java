package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.service.LoginService;
import pl.jarekit.rael.service.SubscriptionService;

@Controller
public class SubscriptionController {
    
    private SubscriptionService subscriptionService;
    private LoginService loginService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, LoginService loginService) {
        this.subscriptionService = subscriptionService;
        this.loginService = loginService;
    }

    @GetMapping("/subscription")
    public String showSubscriptionPage(Model model) {
        model.addAttribute("title", "subscription page");
        model.addAttribute("expireDate", loginService.getUser().getExpireDate());
        model.addAttribute("generatedCode", subscriptionService.generateCode());
        return "subscription";
    }

    @GetMapping("/subscription/{code}")
    public String showSubscriptionPage2(@PathVariable int code, @AuthenticationPrincipal User user) {
        subscriptionService.extendSubscription(code, user);
        return "redirect:/subscription";
    }
}
