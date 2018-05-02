package no.octopod.backend.service;

import no.octopod.backend.entity.Book;
import no.octopod.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BookService {

    @Autowired
    private EntityManager em;

    @Autowired
    private UserService userService;

    public Book createBook(String isbn, String title, List<String> authors, String course) {
        TypedQuery<Long> duplicateBookQuery =
                em.createQuery("select count(b) from Book b where b.isbn=?1", Long.class);
        duplicateBookQuery.setParameter(1, isbn);
        long size = duplicateBookQuery.getSingleResult();

        if (size > 0) {
            return null;
        }

        Book book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setCourse(course);
        book.setAuthors(authors);

        em.persist(book);
        return book;
    }

    public Book getBook(String isbn) {
        TypedQuery<Book> getBookQuery =
                em.createQuery("select b from Book b where b.isbn=?1", Book.class);
        getBookQuery.setParameter(1, isbn);
        return getBookQuery.getSingleResult();
    }

    public Book getBook(Long id) {
        return em.find(Book.class, id);
    }

    public void deleteBook(Long id) {
        deleteBook(getBook(id));
    }

    public void deleteBook(Book book) {
        if (book.getId() != null) {
            Book attached = em.merge(book);
            em.remove(attached);
        }
    }

    public List<Book> getAllBooks() {
        TypedQuery<Book> query =
                em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    public List<Book> getAllBooksWithSellers() {
        TypedQuery<Book> query =
                em.createQuery("select b from Book b where size(b.sellers) >= 1", Book.class);
        return query.getResultList();
    }

    public Book updateBook(Book book) {
        return em.merge(book);
    }

    public boolean toggleForSaleByUser(Long userId, Long bookId) {
        boolean forSale = false;

        User user = userService.getUser(userId);
        Book book = getBook(bookId);

        if (book.getSellers().contains(user)) {
            user.getBooksForSale().remove(book);
            book.getSellers().remove(user);
        } else {
            user.getBooksForSale().add(book);
            book.getSellers().add(user);
            forSale = true;
        }

        userService.updateUser(user);
        updateBook(book);
        return forSale;
    }
}
