package pl.jarekit.rael;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.jarekit.rael.secure.JwtFilter;
import pl.jarekit.rael.service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http.authorizeRequests()
                .antMatchers("/auth").permitAll()

                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .antMatchers("/receiveIssues").hasAuthority("ROLE_ADMIN")
                .antMatchers("/deleteIssue", "/deleteIssue/*").hasAuthority("ROLE_ADMIN")

                .antMatchers("/home").authenticated()
                .antMatchers("/address*","/addressEdit/*").authenticated()
                .antMatchers("/client*","/clientEdit/*").authenticated()
                .antMatchers("/invoice*","/invoiceEdit/*").authenticated()
                .antMatchers("/book","/book/*").authenticated()
                .antMatchers("/summary","/summary/*").authenticated()
                .antMatchers("/subscription","subscription/*").authenticated()
                .antMatchers("/API/address/*").authenticated()
                .antMatchers("/sendIssue").authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")

                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("JSESSIONID")

                .and()
                .httpBasic()

                .and()
                .addFilter(new JwtFilter(authenticationManager())
                );
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
