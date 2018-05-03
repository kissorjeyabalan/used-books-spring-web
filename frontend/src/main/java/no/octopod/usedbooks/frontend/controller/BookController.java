package no.octopod.usedbooks.frontend.controller;

import no.octopod.usedbooks.backend.entity.Book;
import no.octopod.usedbooks.backend.entity.User;
import no.octopod.usedbooks.backend.service.BookService;
import no.octopod.usedbooks.frontend.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
@RequestScoped
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthenticatedUser au;

    private String isbn;
    private String title;
    private String authors;
    private String course;

    public String sellBook() {
        String[] authorNames = authors.trim().split("\\s*,\\s*");
        List<String> authorList = new ArrayList<>(Arrays.asList(authorNames));

        Book book;
        try {
            book = bookService.createBook(isbn, title, authorList, course);
        } catch (Exception e) {
            return "/sellbook.jsf?faces-redirect=true&error=lfs";
        }
        System.out.println("USER ID: " + au.getUser().getId());
        bookService.toggleForSaleByUser(1L, book.getId());

        return "/index.jsf?faces-redirect=true";
    }


    public List<Book> getAvailableBooks() {
        return bookService.getAllBooksWithSellers();
    }

    public List<Book> getAllBooks() { return bookService.getAllBooks(); }

    public String toggleSeller(Long userId, Long bookId) {
        bookService.toggleForSaleByUser(userId, bookId);
        return "/index.jsf?faces-redirect=true";
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
