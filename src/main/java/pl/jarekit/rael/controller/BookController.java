package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.jarekit.rael.model.Invoice;
import pl.jarekit.rael.service.InvoiceService;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class BookController {

    InvoiceService invoiceService;

    @Autowired
    public BookController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/book")
    public String showEmptyBookPage(Model model) {
        model.addAttribute("title", "all invoices in Book");

        // create invoices List
        List<Invoice> invoices = new ArrayList<>();

        // get all invoices and add to List
        invoiceService.getInvoices().forEach(invoices::add);

        // sort List by DateCreate
        invoices.sort(Comparator.comparing(Invoice::getDateCreate));

        // set amount by invoice category
        putAmountByCategory(invoices);


        model.addAttribute("invoices", invoices);

        BigDecimal[] sumAmountAllTime = SumAmountFromThisPeriod(invoices);
        model.addAttribute("sumAmountAllTime", sumAmountAllTime);

        return "bookSummary";
    }



    @GetMapping("/book/{yyyy}-{mm}")
    public ModelAndView showBookPage(@PathVariable int yyyy, @PathVariable int mm) {
        ModelAndView mav = new ModelAndView("book");
        mav.addObject("title", "Book: "+yyyy+"/"+mm);


        // create invoices List
        List<Invoice> invoices = new ArrayList<>();

        // get all invoices and add to List
        invoiceService.getInvoices().forEach(invoices::add);

        // create empty filtered list
        List<Invoice> invoicesFilteredByPeriod = filterByYearAndMonth(invoices, yyyy, mm);

        // sort List by DateCreate
        invoicesFilteredByPeriod.sort(Comparator.comparing(Invoice::getDateCreate));

        // set amount by invoice category
        putAmountByCategory(invoicesFilteredByPeriod);

        mav.addObject("invoices", invoicesFilteredByPeriod);

        BigDecimal[] sumAmountPreviousMonth = SumAmountFromBeginningOfTheYear(invoices, yyyy, mm);
        mav.addObject("sumAmountFromTheBeginningOfTheYear", sumAmountPreviousMonth);

        BigDecimal[] sumAmountThisMonth = SumAmountFromThisPeriod(invoicesFilteredByPeriod);
        mav.addObject("sumAmountThisMonth", sumAmountThisMonth);

        return mav;
    }


/*
* todo Separately method to get all invoices in typed year (eg. /book/2019)
*
    @GetMapping("/book/{yyyy}")
    public ModelAndView showBookPage(@PathVariable int yyyy) {
        ModelAndView mav = new ModelAndView("book");
        mav.addObject("title", "Book "+yyyy);


        // create invoices List
        List<Invoice> invoices = new ArrayList<>();

        // get all invoices and add to List
        invoiceService.getInvoices().forEach(invoices::add);

        // create empty filtered list
        List<Invoice> invoicesFilteredByPeriod = filterByYear(invoices, yyyy);

        // sort List by DateCreate
        invoicesFilteredByPeriod.sort(Comparator.comparing(Invoice::getDateCreate));

        // set amount by invoice category
        putAmountByCategory(invoicesFilteredByPeriod);

        mav.addObject("invoices", invoicesFilteredByPeriod);

        BigDecimal[] sumAmountSelectedYear = SumAmountFromBeginningOfTheYear(invoices, yyyy, 12);
        mav.addObject("sumAmountFromTheBeginningOfTheYear", sumAmountSelectedYear);

        BigDecimal[] sumAmountThisMonth = SumAmountFromThisMonth(invoicesFilteredByPeriod);
        mav.addObject("sumAmountThisMonth", sumAmountThisMonth);

        return mav;
    }
*/





    private List<Invoice> filterByYearAndMonth(List<Invoice> invoices, int yyyy, int mm) {

        List<Invoice> invoicesFiltered = new ArrayList<>();

        invoices.stream()
                .filter(invoice -> invoice.getPeriod().getYear() == yyyy)
                .filter(invoice -> invoice.getPeriod().getMonthValue() == mm)
                .forEach(invoicesFiltered::add);
        return invoicesFiltered;
    }

    private List<Invoice> filterByYear(List<Invoice> invoices, int yyyy) {

        List<Invoice> invoicesFiltered = new ArrayList<>();

        invoices.stream()
                .filter(invoice -> invoice.getPeriod().getYear() == yyyy)
                .forEach(invoicesFiltered::add);
        return invoicesFiltered;
    }

    private List<Invoice> putAmountByCategory(List<Invoice> invoices) {

        invoices.forEach(invoice ->
        {
            switch (invoice.getCategory()) {
                case 7:
                    invoice.getAmountType()[0] = invoice.getAmount();
                    break;
                case 8:
                    invoice.getAmountType()[1] = invoice.getAmount();
                    break;
                case 12:
                    invoice.getAmountType()[2] = invoice.getAmount();
                    break;
                case 13:
                    invoice.getAmountType()[3] = invoice.getAmount();
                    break;
                default:
                    throw new IllegalStateException("Unexpected category: " + invoice.getCategory());
            }
        });
        return invoices;
    }

    private BigDecimal[] SumAmountFromBeginningOfTheYear(List<Invoice> invoices, int yyyy, int mm) {

        // initialization BigDecimal to 0 to avoid NullPointException
        BigDecimal[] sumAmount = new BigDecimal[6];
        bigDecimalInitialization(sumAmount);

        invoices.stream()
                .filter(invoice -> invoice.getPeriod().getYear() == yyyy)
                .filter(invoice -> invoice.getPeriod().getMonthValue() <= mm)
                .forEach(invoice -> {
                    switch (invoice.getCategory()) {
                        case 7:
                            sumAmount[0] = sumAmount[0].add(invoice.getAmount());
                            break;
                        case 8:
                            sumAmount[1] = sumAmount[1].add(invoice.getAmount());
                            break;
                        case 12:
                            sumAmount[2] = sumAmount[2].add(invoice.getAmount());
                            break;
                        case 13:
                            sumAmount[3] = sumAmount[3].add(invoice.getAmount());
                            break;
                    }
                });

        sumAmount[4] = sumAmount[0].add(sumAmount[1]);
        sumAmount[5] = sumAmount[2].add(sumAmount[3]);

        return sumAmount;
    }

    private BigDecimal[] SumAmountFromThisPeriod(List<Invoice> invoices) {

        // initialization BigDecimal to 0 to avoid NullPointException
        BigDecimal[] sumAmount = new BigDecimal[6];
        bigDecimalInitialization(sumAmount);

        for (Invoice invoice : invoices) {
            switch (invoice.getCategory()) {
                case 7:
                    sumAmount[0] = sumAmount[0].add(invoice.getAmount());
                    break;
                case 8:
                    sumAmount[1] = sumAmount[1].add(invoice.getAmount());
                    break;
                case 12:
                    sumAmount[2] = sumAmount[2].add(invoice.getAmount());
                    break;
                case 13:
                    sumAmount[3] = sumAmount[3].add(invoice.getAmount());
                    break;
            }
        }

        sumAmount[4] = sumAmount[0].add(sumAmount[1]);
        sumAmount[5] = sumAmount[2].add(sumAmount[3]);

        return sumAmount;
    }

    private void bigDecimalInitialization(BigDecimal[] sumAmount) {
        sumAmount[0] = BigDecimal.ZERO;
        sumAmount[1] = BigDecimal.ZERO;
        sumAmount[2] = BigDecimal.ZERO;
        sumAmount[3] = BigDecimal.ZERO;
        sumAmount[4] = BigDecimal.ZERO;
        sumAmount[5] = BigDecimal.ZERO;
    }

}

