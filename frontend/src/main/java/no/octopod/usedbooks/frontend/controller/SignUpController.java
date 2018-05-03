package no.octopod.usedbooks.frontend.controller;

import no.octopod.usedbooks.backend.entity.User;
import no.octopod.usedbooks.backend.service.UserService;
import no.octopod.usedbooks.frontend.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SignUpController {
    @Autowired private UserService userService;
    @Autowired private AuthenticatedUser authenticatedUser;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsService userDetailsService;

    private String email;
    private String password;
    private String firstName;
    private String lastName;


    public String signUp() {
        User user = null;
        try {
            user = userService.createUser(email, password, firstName, lastName);
        } catch (Exception e) {
            return "/signup.jsf?faces-redirect=true&error=true";
        }
        if (user != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails, password, userDetails.getAuthorities());
            authenticationManager.authenticate(token);
            if (token.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(token);
                authenticatedUser.setUser(user);
                return "/index.jsf?faces-redirect=true";
            }
        }
        return "/signup.jsf?faces-redirect=true&error=true";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
