package pl.jarekit.rael;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
import java.time.LocalDate;
import java.util.Arrays;

//@Component
@RestController
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

    User user0 = new User();
    User user1 = new User();
    User user2 = new User();


//    @EventListener(ApplicationReadyEvent.class)
    public void runDemoRepo() {

        // set user 0 - ADMIN
        user0.setUsername("admin@JarekIT.pl");
        user0.setPassword("adminadmin");
        userService.addAdmin(user0);

        // set user 1 - USER
        user1.setUsername("Jarek@JarekIT.pl");
        user1.setPassword("JJJJJJ");
        userService.addUser(user1);

        // set user 2 - USER
        user2.setUsername("Jaroslaw@JarekIT.pl");
        user2.setPassword("JJJ");
        userService.addUser(user2);
    }

    @GetMapping("/run")
    public void runDemoRepo2() {

        // save one Address x6
        Address newAddress1 = new Address("Poprzeczna", "13", "3", "81-627", "Gdynia",user1);
        Address newAddress2 = new Address("Graniczna", "1", "2", "81-626", "Gdynia",user1);
        Address newAddress3 = new Address("Norwida", "6", "7", "80-111", "Gdańsk",user1);
        Address newAddress4 = new Address("Cwiartki", "3", "4", "00-020", "Warszawa",user1);
        Address newAddress5 = new Address("al. Niepodległości", "200", "2", "81-626", "Sopot",user1);
        Address newAddress6 = new Address("Długa", "5", "2", "83-200", "Wejherowo",user1);

        Iterable<Address> addresses123 = Arrays.asList(newAddress1,newAddress2,newAddress3,newAddress4,newAddress5,newAddress6);
        addressService.saveAddresses(addresses123);

        // save Client and Address (separately)
        Address newAddress0 = new Address("GranicznaXYZ111", "1", "1", "81-626", "Gdynia",user1);
        addressService.saveAddress(newAddress0);
        Client client = new Client("Jarek1 Jarek1", BigInteger.valueOf(1234567890), "", "", BigInteger.TEN,user1);
        client.setAddress(newAddress0);
        clientService.saveClient(client);

        // save Client and Address (one instance)
        Client client2 = new Client("Jarek2 Jarek2", BigInteger.valueOf(234567890), "", "", BigInteger.ZERO,user1);
        client2.setAddress(addressService.getAddressById(2L));
        clientService.saveClient(client2);

        int c = 3;
        while (c<=6){
            Client clientX = new Client("Jarek"+c, BigInteger.valueOf(1000000+c), "", "", null,user1);
            clientX.setAddress(addressService.getAddressById(c));
            clientService.saveClient(clientX);
            c++;
        }



        // 9 invoice add
        int i = 1;
        while (i < 5 ) {
            Invoice invoice1 = new Invoice();
            invoice1.setAmount(BigDecimal.valueOf(1000.01 * i));
            invoice1.setClientBuyer(client);
            invoice1.setClientSeller(client2);
            invoice1.setType("FS");
            invoice1.setClientPartner(client);
            invoice1.setComment("comment");
            invoice1.setDescription("description");
            invoice1.setDateCreate( LocalDate.now()     );
            invoice1.setDateSale(   LocalDate.now()     );
            invoice1.setPeriod(     LocalDate.now()     );
            invoice1.setInvoiceNumber("Inv 20/39281/" + i );
            invoice1.setCategory((i%2==0) ? 7 : 8);
            invoice1.setUser(user1);
            invoiceService.saveInvoice(invoice1);
            i++;
        }
        while (i < 10 ) {
            Invoice invoice1 = new Invoice();
            invoice1.setAmount(BigDecimal.valueOf(1000.01 * i));
            invoice1.setClientBuyer(client2);
            invoice1.setClientSeller(client);
            invoice1.setType("FK");
            invoice1.setClientPartner(client2);
            invoice1.setComment("comment");
            invoice1.setDescription("description");
            invoice1.setDateCreate( LocalDate.now()     );
            invoice1.setDateSale(   LocalDate.now()     );
            invoice1.setPeriod(     LocalDate.now()     );
            invoice1.setInvoiceNumber("Rach 124/" + i );
            invoice1.setCategory((i%2==0) ? 12 : 13);
            invoice1.setUser(user1);
            invoiceService.saveInvoice(invoice1);
            i++;
        }
        while (i < 14 ) {
            Invoice invoice1 = new Invoice();
            invoice1.setAmount(BigDecimal.valueOf(111.01 * i));
            invoice1.setClientBuyer(client);
            invoice1.setClientSeller(client2);
            invoice1.setType("FS");
            invoice1.setClientPartner(client);
            invoice1.setComment("comment");
            invoice1.setDescription("description");
            invoice1.setDateCreate( LocalDate.now().minusMonths(1)     );
            invoice1.setDateSale(   LocalDate.now().minusMonths(1)     );
            invoice1.setPeriod(     LocalDate.now().minusMonths(1)     );
            invoice1.setInvoiceNumber("Inv 20/234/" + i );
            invoice1.setCategory((i%2==0) ? 7 : 8);
            invoice1.setUser(user1);
            invoiceService.saveInvoice(invoice1);
            i++;
        }


        System.out.println("- - - - - - - - - - - - -");
        System.out.println("- - - - - - - - - - - - -");
        System.out.println("- - - - - E N D - - - - -");
        System.out.println("- - - - - - - - - - - - -");
        System.out.println("- - - - - - - - - - - - -");

    }

}
