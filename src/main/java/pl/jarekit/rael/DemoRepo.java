package pl.jarekit.rael;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.service.AddressService;

import java.util.Arrays;

@Component
public class DemoRepo {

    private AddressService addressService;

    @Autowired
    public DemoRepo(AddressService addressService) {
        this.addressService = addressService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runDemoRepo(){

        // save one Address x6
        Address newAddress1 = new Address("Poprzeczna", "13", "3", "81-627", "Gdynia");
        Address newAddress2 = new Address("Graniczna", "1", "2", "81-626", "Gdynia");
        Address newAddress3 = new Address("Norwida", "6", "7", "80-111", "Gdańsk");
        Address newAddress4 = new Address("Cwiartki", "3", "4", "00-020", "Warszawa");
        Address newAddress5 = new Address("al. Niepodległości", "200", "2", "81-626", "Sopot");
        Address newAddress6 = new Address("Długa", "5", "2", "83-200", "Wejherowo");

        Iterable<Address> addresses123 = Arrays.asList(newAddress1,newAddress2,newAddress3,newAddress4,newAddress5,newAddress6);
        addressService.saveAddresses(addresses123);

        // save Client and Address (separately)
//        Address newAddress0 = new Address("GranicznaXYZ111", "1", "1", "81-626", "Gdynia");
//        addressRepo.save(newAddress0);
//        Client client = new Client("Jarek Jarek", BigInteger.valueOf(1234567890), "", "", BigInteger.TEN);
//        client.setAddress(newAddress0);
//        clientRepo.save(client);

        // save Client and Address (one instance)
//        Client client2 = new Client("Jarek2 Jarek2", BigInteger.valueOf(234567890), "", "", BigInteger.TEN);
//        client2.setAddress(addressService.getAddressById(2L));
//        clientRepo.save(client2);

        // write in console all addresses
        Iterable<Address> allAddresses = addressService.getAddresses();
        allAddresses.forEach(System.out::println);

    }

}
