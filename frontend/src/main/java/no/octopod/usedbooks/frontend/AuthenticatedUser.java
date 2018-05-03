package no.octopod.usedbooks.frontend;

import no.octopod.usedbooks.backend.entity.User;
import no.octopod.usedbooks.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;


@Component
@SessionScoped
public class AuthenticatedUser implements Serializable {

    @Autowired
    private UserService userService;
    private User user;


    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            if (user == null) {
                UserDetails userDetails = ((UserDetails) auth.getPrincipal());
                user = userService.getUser(userDetails.getUsername());
            }
        } else {
            return null;
        }

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
