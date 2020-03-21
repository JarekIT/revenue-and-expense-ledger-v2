package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.service.AddressService;

@Controller
public class AddressController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public String getAddresses(Model model){
        Iterable<Address> addresses = addressService.getAddresses();
        model.addAttribute("addresses",addresses);
        return "address";
    }

    @RequestMapping("/addressAdd")
    public String  showAddressAddPage(Model model){
        model.addAttribute("address",new Address());
        return "addressAdd";
    }

    @RequestMapping(value = "/addressAdd", method = RequestMethod.POST)
    public String saveAddress(@ModelAttribute("address") Address address){
        addressService.saveAddress(address);
        return "redirect:/address";

    }

    @RequestMapping("/addressEdit/{id}")
    public ModelAndView showAddressEditPage(@PathVariable long id){
        ModelAndView mav = new ModelAndView("addressEdit");

        Address loadedAddress = addressService.getAddressById(id);
        if (loadedAddress != null){
            mav.addObject("address",loadedAddress);
        } else {
            mav.addObject("address", new Address());
        }

        return mav;
    }

    @RequestMapping(value = "/addressEdit", method = RequestMethod.POST)
    public String editAddress(@ModelAttribute("address") Address address){
        addressService.saveAddress(address);
        return "redirect:/address";

    }

    @RequestMapping("/addressDelete/{id}")
    public String deleteAddress(@PathVariable long id){
        if (addressService.existById(id)){
            addressService.deleteAddress(id);
        }
        return "redirect:/address";
    }
}
