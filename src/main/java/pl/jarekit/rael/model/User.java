package pl.jarekit.rael.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter @Getter
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String email;

    @Column
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
//    @ManyToMany(targetEntity = Client.class)
    @JoinTable(name="user_client" ,
            joinColumns = @JoinColumn(name = "id_user") ,
            inverseJoinColumns = @JoinColumn(name= "id_client"))
    List<Client> clients;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}' +
                "\n" ;
    }
}
