package pl.jarekit.rael;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.model.Client;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.service.AddressService;
import pl.jarekit.rael.service.ClientService;
import pl.jarekit.rael.service.UserService;

import java.math.BigInteger;
import java.util.Arrays;

@Component
public class DemoRepo {

    private AddressService addressService;
    private ClientService clientService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DemoRepo(AddressService addressService, ClientService clientService, UserService userService, PasswordEncoder passwordEncoder) {
        this.addressService = addressService;
        this.clientService = clientService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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
        Address newAddress0 = new Address("GranicznaXYZ111", "1", "1", "81-626", "Gdynia");
        addressService.saveAddress(newAddress0);
        Client client = new Client("Jarek1 Jarek1", BigInteger.valueOf(1234567890), "", "", BigInteger.TEN);
        client.setAddress(newAddress0);
        clientService.saveClient(client);

        // save Client and Address (one instance)
        Client client2 = new Client("Jarek2 Jarek2", BigInteger.valueOf(234567890), "", "", BigInteger.ZERO);
        client2.setAddress(addressService.getAddressById(2L));
        clientService.saveClient(client2);

        // write in console all addresses
        Iterable<Address> allAddresses = addressService.getAddresses();
        allAddresses.forEach(System.out::println);

        // write in console all clients
        Iterable<Client> allClients = clientService.getClients();
        allClients.forEach(System.out::println);

        // set user 1 - ADMIN
        User user1 = new User();
        user1.setUsername("admin@JarekIT.pl");
        user1.setPassword("adminadmin");
        userService.addAdmin(user1);

        // set user 2 - USER
        User user2 = new User();
        user2.setUsername("Jarek@JarekIT.pl");
        user2.setPassword("JJJJJJ");
        userService.addUser(user2);

        // set user 3 - USER
        User user3 = new User();
        user3.setUsername("Jaroslaw@JarekIT.pl");
        user3.setPassword("JJJ");
        userService.addUser(user3);
    }

}
