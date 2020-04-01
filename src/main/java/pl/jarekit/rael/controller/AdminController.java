package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jarekit.rael.service.AddressService;
import pl.jarekit.rael.service.ClientService;
import pl.jarekit.rael.service.InvoiceService;
import pl.jarekit.rael.service.UserService;

@Controller
public class AdminController {

    private UserService userService;
    private AddressService addressService;
    private ClientService clientService;
    private InvoiceService invoiceService;

    @Autowired
    public AdminController(UserService userService, AddressService addressService, ClientService clientService, InvoiceService invoiceService) {
        this.userService = userService;
        this.addressService = addressService;
        this.clientService = clientService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/admin")
    public String showAllTables(Model model){
        model.addAttribute("addresses",addressService.getAddressesForAdmin());
        model.addAttribute("clients",clientService.getClientsForAdmin());
        model.addAttribute("invoices",invoiceService.getInvoicesForAdmin());
        model.addAttribute("users",userService.getUsersForAdmin());
        return "admin";
    }
}
