package pl.jarekit.rael.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.LogUtils;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.repo.AddressRepo;

import java.util.Optional;

@Service
public class AddressService {

    private AddressRepo addressRepo;

    @Autowired
    public AddressService(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    public Address saveAddress(Address address){
        address.setStatus("active");
        LogUtils.saveLogStatic("Added address: " + address , Level.INFO);
        return addressRepo.save(address);
    }

    public Iterable<Address> saveAddresses(Iterable<Address>  addresses){
        LogUtils.saveLogStatic("Added list of addresses: " + addresses , Level.INFO);
        return addressRepo.saveAll(addresses);
    }

    public Iterable<Address> getAddresses(){
        LogUtils.saveLogStatic("Loaded all addresses" , Level.INFO);
        return addressRepo.findAll();
    }

    public Address getAddressById(long id) {
        Optional<Address> loadedAddress = addressRepo.findById(id);
        if (loadedAddress.isPresent()) {
            LogUtils.saveLogStatic("Loaded address: " + loadedAddress, Level.INFO);
            return loadedAddress.get();
        } else {
            LogUtils.saveLogStatic("Failed Loaded all by id: " + id, Level.WARNING);
            return loadedAddress.orElse(null);
        }
    }


    public Address updateAddress(Address address){

        if (addressRepo.existsById(address.getId())) {
            Address addressToEdit = addressRepo.findById((address.getId())).get();
            String log = "Edited address: from: " + addressToEdit;
            addressToEdit.setApartmentNumber(address.getApartmentNumber());
            addressToEdit.setCity(address.getCity());
            addressToEdit.setHouseNumber(address.getHouseNumber());
            addressToEdit.setPostcode(address.getPostcode());
            addressToEdit.setStreet(address.getStreet());
            log = log + ", to: " + addressToEdit;
            LogUtils.saveLogStatic(log, Level.INFO);
            return addressRepo.save(addressToEdit);
        } else{
            LogUtils.saveLogStatic( "Failed: Edited address, can't find address id: " + address.getId() +
                    ", so created new Address: " + address, Level.WARNING);
            return addressRepo.save(address);
        }
    }

    public String deleteAddress(long id){
        Optional<Address> addressToDelete = addressRepo.findById(id);
        if (addressToDelete.isPresent()){
            addressRepo.deleteById(id);
            LogUtils.saveLogStatic("Deleted address: " + addressToDelete, Level.INFO);
            return "Deleted address: " + addressToDelete;
        } else {
            LogUtils.saveLogStatic("Failed Deleted address by id: " + id, Level.WARNING);
            return "Failed Deleted address by id: " + id;
        }
    }

    public boolean existById(long id){
        return addressRepo.existsById(id);
    }
}
