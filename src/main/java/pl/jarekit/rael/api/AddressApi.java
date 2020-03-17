package pl.jarekit.rael.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.Log;
import pl.jarekit.rael.logs.LogUtils;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.repo.AddressRepo;
import pl.jarekit.rael.repo.ClientRepo;

import java.util.Optional;

@RestController
@RequestMapping("/api/address/")
public class AddressApi {

    private AddressRepo addressRepo;
    private ClientRepo clientRepo;

    @Autowired
    public AddressApi(AddressRepo addressRepo, ClientRepo clientRepo) {
        this.addressRepo = addressRepo;
        this.clientRepo = clientRepo;
    }

    @GetMapping("/all")
    public Iterable<Address> getAll() {
        LogUtils.saveLogStatic("Loaded all addresses" , Level.INFO);
        return addressRepo.findAll();
    }

    @GetMapping("/{id}")
    public Address getById(@PathVariable long id) {
        Optional<Address> loadedAddress = addressRepo.findById(id);
        if (loadedAddress.isPresent()){
            LogUtils.saveLogStatic("Loaded Address: " + loadedAddress , Level.INFO);
            return loadedAddress.get();
        } else {
            LogUtils.saveLogStatic("Failed loading attempt to address id: " + id , Level.WARNING);
            return loadedAddress.orElse(null);
        }

    }


    @PostMapping
    public void addAddress(@RequestBody Address address) {
        LogUtils.saveLogStatic("Added address: " + address , Level.INFO);
        addressRepo.save(address);
    }

    @DeleteMapping("/{id}")
    public void removeAddress(@PathVariable long id) {
        Optional<Address> addressToDelete = addressRepo.findById(id);
        if (addressToDelete.isPresent()) {
            LogUtils.saveLogStatic("Deleted address: " + addressToDelete , Level.INFO);
            addressRepo.deleteById(id);
        } else {
            LogUtils.saveLogStatic("Failed deleting attempt to address id: " + id , Level.WARNING);
        }
    }

    @PutMapping("/edit")
    public Address updateAddress(@RequestBody Address address){
        LogUtils.saveLogStatic("Updated address: " + address , Level.INFO);
        return addressRepo.save(address);
    }
}
