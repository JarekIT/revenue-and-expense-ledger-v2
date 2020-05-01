package pl.jarekit.rael.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Setter @Getter
@Entity
public class Address {


    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column private
    String street;

    @Column(name="house_number")
    private String houseNumber;

    @Column(name="apartment_number")
    private String apartmentNumber;

    @Size(min = 5, max = 6, message = "postcode must be between 5-6 chars")
    @Column
    private String postcode;

    @Column
    private String city;

    @Column
    private String status;

    @OneToOne
    @JsonBackReference
    private User user;

    public Address() {
    }

    public Address(String street, String houseNumber, String apartmentNumber, String postcode, String city, User user) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.postcode = postcode;
        this.city = city;
        this.status = "active";
        this.user = user;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", status='" + status + '\'' +
                '}' +
                "\n" ;
    }
}
