package pl.jarekit.rael.secure;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

public class JwtFilter extends BasicAuthenticationFilter {

    private final String SECRET_KEY = "secret!@#$%";

    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        final String header = request.getHeader("Authorization");

        if ( header != null && header.substring(0,6).equals("Bearer")) {
            UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(header);
            SecurityContextHolder.getContext().setAuthentication(authResult);
        }

        chain.doFilter(request,response);
    }


    private UsernamePasswordAuthenticationToken getAuthenticationByToken (String header) {

        try {
            String cleanHeader = header.replace("Bearer ", "");

            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(cleanHeader);

            String username = claimsJws.getBody().get("username").toString();
            String password = claimsJws.getBody().get("password").toString();
            String role = claimsJws.getBody().get("role").toString();
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = Collections.singleton(new SimpleGrantedAuthority(role));

            return new UsernamePasswordAuthenticationToken(username, password, simpleGrantedAuthorities);
        } catch (NullPointerException e) {
            return null;
        }
    }



}
