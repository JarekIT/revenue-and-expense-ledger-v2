package pl.jarekit.rael.secure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationRequestModel {

    private String username;
    private String password;


}
