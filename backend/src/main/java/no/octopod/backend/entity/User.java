package no.octopod.backend.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank @Size(min = 2, max = 28)
    private String firstName;

    @NotBlank @Size(min = 2, max = 28)
    private String lastName;

    @ManyToMany
    private List<Book> booksForSale;

    public User() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBooksForSale() {
        return booksForSale;
    }

    public void setBooksForSale(List<Book> booksForSale) {
        this.booksForSale = booksForSale;
    }
}
