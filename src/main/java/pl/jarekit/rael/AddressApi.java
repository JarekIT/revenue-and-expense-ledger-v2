package pl.jarekit.rael;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return addressRepo.findAll();
    }

    @GetMapping("/{id}")
    public Address getById(@PathVariable long id) {
        Optional<Address> first = addressRepo.findById(id);
        return first.orElse(null);
    }


    @PostMapping
    public void addAddress(@RequestBody Address address) {
        addressRepo.save(address);
    }

    @DeleteMapping("/{id}")
    public void removeAddress(@PathVariable long id) {
        Optional<Address> didIdExist = addressRepo.findById(id);
        if (didIdExist.isPresent()) {
            addressRepo.deleteById(id);
        }
    }
}
