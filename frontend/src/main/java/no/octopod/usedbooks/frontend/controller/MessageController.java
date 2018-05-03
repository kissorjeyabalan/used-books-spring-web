/*package no.octopod.usedbooks.frontend.controller;

import no.octopod.usedbooks.backend.entity.Message;
import no.octopod.usedbooks.backend.entity.User;
import no.octopod.usedbooks.backend.service.MessageService;
import no.octopod.usedbooks.backend.service.UserService;
import no.octopod.usedbooks.frontend.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class MessageController {

    private String formText;
    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthenticatedUser au;

    public List<Message> getMessages() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        System.out.println("PARAMETER " + req.getParameter("receiver"));
        return new ArrayList<>();
        //return messageService.getMessagesBetween(au.getUser().getId(), Long.parseLong(recipient));
    }

    public String messageUser(String id) {
        messageService.createMessage(au.getUser().getId(), Long.parseLong(id), formText);
        formText = "";

        return "/index.jsf";
    }

    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }
}
*/