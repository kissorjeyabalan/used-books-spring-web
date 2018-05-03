package no.octopod.usedbooks.frontend.controller;

import no.octopod.usedbooks.frontend.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class UserInfoController {

    @Autowired
    private AuthenticatedUser user;

    public Long getUserId() {
        return user.getUser().getId();
    }

}
