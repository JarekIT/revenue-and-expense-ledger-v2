package pl.jarekit.rael.systems.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublisherController {

    private PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping("/sendIssue")
    public Object getNewIssuePage(Model model) {
        model.addAttribute("title", "Issue sender");
        return "issueUser";
    }

    @PostMapping("/sendIssue")
    public String get(@RequestParam String topic, @RequestParam String message) {
        publisherService.publishMessage(topic, message);
        return "redirect:/sendIssue";
    }
}
