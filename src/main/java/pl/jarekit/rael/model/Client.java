package pl.jarekit.rael.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Setter @Getter
@Entity
public class Client {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="company_name")
    private String companyName;

    @Column(name="company_number")
    private BigInteger companyNumber;

    @Column(name="personal_name")
    private String personalName;

    @Column(name="personal_surname")
    private String personalSurname;

    @Column(name="personal_number")
    private BigInteger personalNumber;


    @OneToOne(cascade= CascadeType.MERGE)
    Address address;

    @Column(name="account_number")
    private BigInteger accountNumber;

    @Column
    private String status;

//    @OneToOne
//    @JoinColumn(name="id_user_company")
//    Client user;

    @OneToOne
    private User user;


    @OneToMany(mappedBy = "clientSeller", fetch = FetchType.EAGER)
    Set<Invoice> invoiceSaleListFromClient;

    @OneToMany(mappedBy = "clientBuyer", fetch = FetchType.EAGER)
    Set<Invoice> invoiceBuyListFromClient;


    @ManyToMany(mappedBy = "clients",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    List<User> users;

    public Client(String companyName, BigInteger companyNumber, String personalName, String personalSurname, BigInteger personalNumber, User user) {
        this.companyName = companyName;
        this.companyNumber = companyNumber;
        this.personalName = personalName;
        this.personalSurname = personalSurname;
        this.personalNumber = personalNumber;
        this.status = "active";
        this.user = user;
    }

    public Client() {

    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", personalName='" + personalName + '\'' +
                ", personalSurname='" + personalSurname + '\'' +
                ", personalNumber=" + personalNumber +
                ", address=" + address +
                ", accountNumber=" + accountNumber +
                ", status='" + status + '\'' +
//                ", \n address=" + address +
//                ", \n user=" + user +
                '}' +
                "\n";
    }
}


