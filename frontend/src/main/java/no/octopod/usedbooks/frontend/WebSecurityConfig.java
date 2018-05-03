package no.octopod.usedbooks.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers("/", "/index.jsf", "/signup.jsf", "/details.jsf", "/assets/**").permitAll()
                    .antMatchers("/javax.faces.resource/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login.jsf")
                    .permitAll()
                    .failureUrl("/login.jsf?error=true")
                    .defaultSuccessUrl("/index.jsf")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/index.jsf");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery("SELECT email, password, id FROM user WHERE email = ?")
                    .authoritiesByUsernameQuery(
                            "SELECT x.email, y.roles FROM user x, user_roles y WHERE x.email = ? and y.user_id = x.id"
                    )
                    .passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
