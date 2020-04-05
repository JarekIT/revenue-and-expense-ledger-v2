package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.service.SummaryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class SummaryController {

    private List<Map<Integer, BigDecimal>> summaryYear;

    private SummaryService taxService;

    @Autowired
    public SummaryController(SummaryService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("/summary")
    public String summarizeEmptyYear(Model model){
        model.addAttribute("title","Summary");
        model.addAttribute("yearText","(write year)");
        model.addAttribute("taxRateText","(write tax rate)");

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user",user.getClientUser());

        summaryYear = taxService.putZerosInSummaryEmptyFields();
        model.addAttribute("summaryYear",summaryYear);

        return "summary";
    }

    @GetMapping("/summary/{year}-{taxRate}")
    public String summarizeTaxYear(@PathVariable int year, @PathVariable int taxRate, Model model){
        model.addAttribute("title","Summary tax year: " + year);
        model.addAttribute("yearText",year);
        model.addAttribute("taxRateText",taxRate);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user",user.getClientUser());

        summaryYear = taxService.summarizeTaxYear(year, taxRate);
        model.addAttribute("summaryYear",summaryYear);
        return "summary";
    }
}
