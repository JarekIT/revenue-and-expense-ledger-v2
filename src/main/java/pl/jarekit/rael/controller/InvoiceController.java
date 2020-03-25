package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.jarekit.rael.model.Invoice;
import pl.jarekit.rael.service.AddressService;
import pl.jarekit.rael.service.ClientService;
import pl.jarekit.rael.service.InvoiceService;

@Controller
public class InvoiceController {

    private ClientService clientService;
    private InvoiceService invoiceService;
    private AddressService addressService;

    @Autowired
    public InvoiceController(ClientService clientService, InvoiceService invoiceService, AddressService addressService) {
        this.clientService = clientService;
        this.invoiceService = invoiceService;
        this.addressService = addressService;
    }


    @GetMapping("/invoice")
    public String getInvoices(Model model){
        model.addAttribute("title","Invoices");
        Iterable<Invoice> invoices = invoiceService.getInvoices();
        model.addAttribute("invoices",invoices);
        return "invoice";
    }

    @RequestMapping("/invoiceAdd")
    public String  showInvoiceAddPage(Model model){
        model.addAttribute("title","Invoice Add");
        model.addAttribute("invoice",new Invoice());
        model.addAttribute("clients",clientService.getClients());
        model.addAttribute("addresses",addressService.getAddresses());

        return "invoiceAdd";
    }

    @RequestMapping(value = "/invoiceAdd", method = RequestMethod.POST)
    public String saveInvoice(@ModelAttribute("invoice") Invoice invoice){
        invoiceService.saveInvoice(invoice);
        return "redirect:/invoice";

    }

    @RequestMapping("/invoiceEdit/{id}")
    public ModelAndView showInvoiceEditPage(@PathVariable long id){
        ModelAndView mav = new ModelAndView("invoiceEdit");
        mav.addObject("title","Invoice Edit");
        mav.addObject("clients",clientService.getClients());
        mav.addObject("addresses",addressService.getAddresses());
        Invoice loadedInvoice = invoiceService.getInvoiceById(id);
        if (loadedInvoice != null){
            mav.addObject("invoice",loadedInvoice);
        } else {
            mav.addObject("invoice", new Invoice());
        }

        return mav;
    }

    @RequestMapping(value = "/invoiceEdit", method = RequestMethod.POST)
    public String editInvoice(@ModelAttribute("invoice") Invoice invoice){
        invoiceService.saveInvoice(invoice);
        return "redirect:/invoice";

    }

    @RequestMapping("/invoiceDelete/{id}")
    public String deleteInvoice(@PathVariable long id){
        if (invoiceService.existById(id)){
            invoiceService.deleteInvoice(id);
        }
        return "redirect:/invoice";
    }


}
