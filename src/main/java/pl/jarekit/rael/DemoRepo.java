package pl.jarekit.rael;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.jarekit.rael.model.Address;
import pl.jarekit.rael.model.Client;
import pl.jarekit.rael.model.Invoice;
import pl.jarekit.rael.model.User;
import pl.jarekit.rael.service.AddressService;
import pl.jarekit.rael.service.ClientService;
import pl.jarekit.rael.service.InvoiceService;
import pl.jarekit.rael.service.UserService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

@Component
public class DemoRepo {

    private AddressService addressService;
    private ClientService clientService;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private InvoiceService invoiceService;

    @Autowired
    public DemoRepo(AddressService addressService, ClientService clientService, UserService userService, PasswordEncoder passwordEncoder, InvoiceService invoiceService) {
        this.addressService = addressService;
        this.clientService = clientService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.invoiceService = invoiceService;
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

        int c = 3;
        while (c<=6){
            Client clientX = new Client("Jarek"+c, BigInteger.valueOf(1000000+c), "", "", null);
            clientX.setAddress(addressService.getAddressById(c));
            clientService.saveClient(clientX);
            c++;
        }

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


        // invoice 10 add
        int i = 1;
        while (i < 10 ) {
            Invoice invoice1 = new Invoice();
            invoice1.setAmount(BigDecimal.valueOf(1000 * i));
            invoice1.setClientBuyer(client);
            invoice1.setClientSeller(client2);
            invoice1.setType("FS");
            invoice1.setClientPartner(client);
            invoice1.setComment("comment");
            invoice1.setDescription("description");
            invoice1.setDateCreate( new Date()     );
            invoice1.setDateSale(   new Date()     );
            invoice1.setPeriod(     new Date()     );
            invoice1.setInvoiceNumber("Inv 20/39281/" + i );
            invoiceService.saveInvoice(invoice1);
            i++;
        }
    }

}
