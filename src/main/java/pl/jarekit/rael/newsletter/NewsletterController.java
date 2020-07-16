package pl.jarekit.rael.newsletter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewsletterController {

    private NewsletterService mailChimpService;

    @Autowired
    public NewsletterController(NewsletterService mailChimpService) {
        this.mailChimpService = mailChimpService;
    }

    @GetMapping("newsletter")
    public String getNewsletterPage(){
        return "newsletterAdd";
    }

    @PostMapping("/newsletter")
    public String addEmailToNewsletter(@RequestParam String email, Model model){
        mailChimpService.addEmailToNewsletter(email);
        model.addAttribute("email",email);
        return "newsletterAddSuccessful";
    }
}
