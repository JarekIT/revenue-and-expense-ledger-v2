package pl.jarekit.rael.systems.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ConsumerController {

    private ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping("/receiveIssues")
    public String receiveMessage(Model model) {
        model.addAttribute("title", "Issue list");
        model.addAttribute("receivedNewIssues", consumerService.receiveNewIssues());
        model.addAttribute("receivedOldIssues", consumerService.receiveOldIssues());
        return "issueAdmin";
    }

    @GetMapping("/deleteIssue/{id}")
    public String deleteMessage(@PathVariable long id) {
        consumerService.deleteMessage(id);
        return "redirect:/receiveIssues";
    }
}

