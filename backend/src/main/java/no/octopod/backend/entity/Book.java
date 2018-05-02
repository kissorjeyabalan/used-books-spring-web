package no.octopod.backend.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank @Size(max = 13)
    private String isbn;

    @NotBlank @Size(min = 2, max = 128)
    private String title;

    @ElementCollection @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> authors;

    private String course;

    @ManyToMany @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> sellers;

    public Book() {
        authors = new ArrayList<>();
        sellers = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public List<User> getSellers() {
        return sellers;
    }
}
