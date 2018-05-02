package no.octopod.backend.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Book {
    @Id @NotBlank @Size(max = 13)
    private String isbn;
    
    @NotBlank @Size(min = 2, max = 128)
    private String title;

    @NotBlank @ElementCollection
    private List<String> authors;

    @NotBlank @ElementCollection
    private List<String> courses;

    @ManyToMany(mappedBy = "booksForSale")
    private List<User> sellers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public List<User> getSellers() {
        return sellers;
    }

    public void setSellers(List<User> sellers) {
        this.sellers = sellers;
    }
}
