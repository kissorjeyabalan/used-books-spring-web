package no.octopod.backend.service;

import no.octopod.backend.AppEntry;
import no.octopod.backend.entity.Book;
import no.octopod.backend.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppEntry.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class BookServiceTest extends ServiceTestBase {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateBook() {
        Book book = bookService.createBook("12341234", "name", getAuthors("Author"), "Course");
        System.out.println(book);
    }
    @Test
    public void testDuplicateBook() {
        Book book = bookService.createBook("97803215733", "BookTitle", getAuthors("Author 1"), "Course");
        Book book2 = bookService.createBook("97803215733", "BookTitle", getAuthors("Author 1"), "Course");

        assertNotNull(book);
        assertNull(book2);
    }

    @Test
    public void testGetAllBooks() {
        bookService.createBook("4314134343434", "Foo", getAuthors("Author 1"), "XYU433");
        bookService.createBook("9780321573513", "Algorithm", getAuthors("Author 2"), "PG4200");
        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
    }


    @Test
    public void testGetBookByIsbn() {
        String isbn = "9780321573513";
        bookService.createBook(isbn, "Algorithm", getAuthors("Author 1"), "PG4200");

        Book book = bookService.getBook(isbn);
        assertEquals(isbn, book.getIsbn());
    }

    @Test
    public void testGetBookById() {
        Book book = bookService.createBook("1234", "asfd", getAuthors("test"), "9Xi48f");
        Book fromDb = bookService.getBook(book.getId());

        assertEquals(book.getId(), fromDb.getId());
    }

    @Test
    public void testGetAllBooksWithSellers() {
        User user = userService.createUser("foo@bar.com", "pass", "first", "sur");
        Book book = bookService.createBook("1432", "for sale", getAuthors("author"), "424232");
        bookService.createBook("4324", "not for sale", getAuthors("author"), "4234423");

        book.getSellers().add(user);
        bookService.updateBook(book);

        List<Book> booksForSale = bookService.getAllBooksWithSellers();
        assertEquals(1, booksForSale.size());
        assertEquals("for sale", booksForSale.get(0).getTitle());

    }

    @Test
    public void testToggleBookSale() {
        Book book = bookService.createBook("429387492", "Book for books", getAuthors("UXK"), "FLS");
        User user = userService.createUser("foo@bar.com", "pass", "firstname", "lastname");

        List<Book> booksForSale = bookService.getAllBooksWithSellers();
        assertEquals(0, booksForSale.size());

        bookService.toggleForSaleByUser(user.getId(), book.getId());
        booksForSale = bookService.getAllBooksWithSellers();
        assertEquals(1, booksForSale.size());

        bookService.toggleForSaleByUser(user.getId(), book.getId());
        booksForSale = bookService.getAllBooksWithSellers();
        assertEquals(0, booksForSale.size());
    }
    private ArrayList<String> getAuthors(String... authors) {
        return new ArrayList<>(Arrays.asList(authors));
    }
}
