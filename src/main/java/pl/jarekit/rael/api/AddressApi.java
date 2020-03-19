package pl.jarekit.rael.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.service.AddressService;

@RestController
@RequestMapping("/api/address/")
public class AddressApi {

    private AddressService addressService;

    @Autowired
    public AddressApi(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public void addAddress(@RequestBody Address address) {
        addressService.saveAddress(address);
    }

    @GetMapping("/all")
    public Iterable<Address> findAllAddresses() {
        return addressService.getAddresses();
    }

    @GetMapping("/{id}")
    public Address findAddressById(@PathVariable long id) {
        return addressService.getAddressById(id);
    }


    @PutMapping("/update")
    public Address updateAddress(@RequestBody Address address){
        return addressService.updateAddress(address);
    }

    @DeleteMapping("/{id}")
    public String removeAddress(@PathVariable long id) {
        return addressService.deleteAddress(id);
    }


}