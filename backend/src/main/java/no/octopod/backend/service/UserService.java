package no.octopod.backend.service;

import no.octopod.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String email, String password, String firstName, String lastName) {
        String hashedPassword = passwordEncoder.encode(password);

        TypedQuery<Long> duplicateUserQuery =
                em.createQuery("select count(u) from User u where u.email=?1", Long.class);
        duplicateUserQuery.setParameter(1, email);
        long size = duplicateUserQuery.getSingleResult();

        if (size > 0) {
            return null;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        em.persist(user);

        return user;
    }

    public User getUser(String email) {
        TypedQuery<User> getUserQuery =
                em.createQuery("select u from User u where u.email=?1", User.class);
        getUserQuery.setParameter(1, email);

        return getUserQuery.getSingleResult();
    }

    public User getUser(long id) {
        return em.find(User.class, id);
    }

    public User updateUser(User user) { return em.merge(user); }
}
