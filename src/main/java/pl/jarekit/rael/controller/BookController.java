package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.jarekit.rael.model.Invoice;
import pl.jarekit.rael.service.InvoiceService;

@Controller
public class BookController {

    InvoiceService invoiceService;

    @Autowired
    public BookController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @RequestMapping("/book/{yyyy}-{mm}")
    public ModelAndView showInvoiceEditPage(@PathVariable long yyyy, @PathVariable long mm){
        ModelAndView mav = new ModelAndView("book");
        mav.addObject("title","Book");



        mav.addObject("clients",clientService.getClients());
        mav.addObject("addresses",addressService.getAddresses());
        Invoice loadedInvoice = invoiceService.getInvoiceById(mm);
        if (loadedInvoice != null){
            mav.addObject("invoice",loadedInvoice);
        } else {
            mav.addObject("invoice", new Invoice());
        }

        return mav;
    }
}
