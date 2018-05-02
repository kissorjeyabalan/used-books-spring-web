package no.octopod.backend.service;

import no.octopod.backend.entity.Book;
import no.octopod.backend.entity.Message;
import no.octopod.backend.entity.User;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Service
@Transactional
public class ResetService {

    @PersistenceContext
    private EntityManager em;

    public void resetDatabase() {
        Query query = em.createNativeQuery("delete from book_authors");
        query.executeUpdate();

        query = em.createNativeQuery("delete from book_sellers");
        query.executeUpdate();

        query = em.createNativeQuery("delete from user_books_for_sale");
        query.executeUpdate();

        deleteEntities(Book.class);
        deleteEntities(User.class);
        deleteEntities(Message.class);
    }

    private void deleteEntities(Class<?> entity) {
        if (entity == null || entity.getAnnotation(Entity.class) == null) {
            throw new IllegalArgumentException("Class is not of type Entity.");
        }

        String name = entity.getSimpleName();

        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();
    }
}
