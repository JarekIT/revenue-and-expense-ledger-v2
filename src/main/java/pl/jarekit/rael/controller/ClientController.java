package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.jarekit.rael.model.Client;
import pl.jarekit.rael.service.AddressService;
import pl.jarekit.rael.service.ClientService;

@Controller
public class ClientController {

    private ClientService clientService;
    private AddressService addressService;

    @Autowired
    public ClientController(ClientService clientService,AddressService addressService) {
        this.clientService = clientService;
        this.addressService = addressService;
    }

    @GetMapping("/client")
    public String getClients(Model model){
        model.addAttribute("title","Clients");
        Iterable<Client> clients = clientService.getClients();
        model.addAttribute("clients",clients);
        return "client";
    }

    @RequestMapping("/clientAdd")
    public String  showClientAddPage(Model model){
        model.addAttribute("title","Client Add");
        model.addAttribute("client",new Client());
        model.addAttribute("addresses",addressService.getAddresses());
        return "clientAdd";
    }

    @RequestMapping(value = "/clientAdd", method = RequestMethod.POST)
    public String saveClient(@ModelAttribute("client") Client client){
        clientService.saveClient(client);
        return "redirect:/client";

    }

    @RequestMapping("/clientEdit/{id}")
    public ModelAndView showClientEditPage(@PathVariable long id){
        ModelAndView mav = new ModelAndView("clientEdit");
        mav.addObject("title","Client Edit");
        mav.addObject("addresses",addressService.getAddresses());
        Client loadedClient = clientService.getClientById(id);
        if (loadedClient != null){
            mav.addObject("client",loadedClient);
        } else {
            mav.addObject("client", new Client());
        }

        return mav;
    }

    @RequestMapping(value = "/clientEdit", method = RequestMethod.POST)
    public String editClient(@ModelAttribute("client") Client client){
        clientService.saveClient(client);
        return "redirect:/client";

    }

    @RequestMapping("/clientDelete/{id}")
    public String deleteClient(@PathVariable long id){
        if (clientService.existById(id)){
            clientService.deleteClient(id);
        }
        return "redirect:/client";
    }


}
