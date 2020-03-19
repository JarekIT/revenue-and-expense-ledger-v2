package pl.jarekit.rael.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.repo.AddressRepo;
import pl.jarekit.rael.service.AddressService;

import java.util.Optional;

@Controller
public class AddressController {

    private AddressRepo addressRepo;
    private AddressService addressService;

    private Iterable<Address> addresses;

    @Autowired
    public AddressController(AddressService addressService, AddressRepo addressRepo) {
        this.addressService = addressService;
        this.addressRepo = addressRepo;
    }

    @PostMapping("/addressAdd")
    public String addAddress(@ModelAttribute Address address){
        addressService.saveAddress(address);
        return "redirect:/address";

    }

    @GetMapping("/address")
    public String getAddresses(Model model){
        addresses = addressService.getAddresses();
        model.addAttribute("addresses",addresses);
        return "address";
    }

    @GetMapping("/address/{id}")
    public String getAddressById(Model model){
        addresses = addressService.getAddresses();
        model.addAttribute("addresses",addresses);
        return "address";
    }






    @RequestMapping(value = {"addressEdit/","addressEdit"})
    public String  editEmptyAddress(Model model){
        model.addAttribute("address",new Address());
        return "addressEdit";
    }

    @RequestMapping(value = "addressEdit/{id}")
    public String  editOneAddress(@PathVariable long id, @ModelAttribute Address address, Model model){
        Optional<Address> loadedAddress = addressRepo.findById(id);
        if (loadedAddress.isPresent()){
            model.addAttribute("address",loadedAddress.get());
        } else {
            model.addAttribute("address",new Address());
        }
        return "addressEdit";
    }




//    @PutMapping
//    public Address updateAddress(@RequestBody Address address){
//        return addressRepo
//    }

//    private void setAddressToHistorical(long id){
//        Optional<Address> address = addressRepo.findById(id);
//
//
//    }

//    @GetMapping("/addressEdit/")
//    @ResponseBody
//    public Address editAddress(@RequestParam long id, Model model){
//        Optional<Address> loadedAddress = addressRepo.findById(id);
//        if (loadedAddress.isPresent()){
//            model.addAttribute("address",loadedAddress.get());
//        } else {
//            model.addAttribute("address",null);
//        }
//
//        return "addressEdit";
//    }
//



}
