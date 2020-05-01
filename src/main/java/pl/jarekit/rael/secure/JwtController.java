package pl.jarekit.rael.secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.jarekit.rael.service.UserDetailsServiceImpl;

@RestController
public class JwtController {

    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userDetailsService;
    private JwtService jwtTokenService;

    @Autowired
    public JwtController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping(value = "/auth")
    public String hello(){
        return "JWT Generator";
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequestModel authenticationRequestModel) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequestModel.getUsername(),
                            authenticationRequestModel.getPassword()
                    ));
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        try {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestModel.getUsername());
            final String jwt = jwtTokenService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponseModel(jwt));
        } catch (Exception e){
            System.out.println(e);
            return null;
        }



    }
}
