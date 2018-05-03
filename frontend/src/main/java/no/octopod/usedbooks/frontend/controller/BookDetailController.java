package no.octopod.usedbooks.frontend.controller;

import no.octopod.usedbooks.backend.entity.Book;
import no.octopod.usedbooks.backend.entity.User;
import no.octopod.usedbooks.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class BookDetailController implements Serializable {

    @Autowired
    private BookService bookService;

    public Book getBook(String isbn) { return bookService.getBook(isbn); }

    public String getAuthorsAsString(String isbn) {
        return String.join(", ", bookService.getBook(isbn).getAuthors());
    }

    public List<User> getSellersForBook(String isbn) {
        Book book = getBook(isbn);
        return book.getSellers();
    }
}
