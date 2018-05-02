package no.octopod.backend.service;

import no.octopod.backend.AppEntry;
import no.octopod.backend.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppEntry.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTest extends ServiceTestBase {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = userService.createUser("foo@bar.com", "password",
                "first", "last");
        assertNotNull(user);
        assertNotNull(user.getId());
    }

    @Test
    public void testCreateDuplicateUser() {
        String email = "foo@bar.com";

        User user = userService.createUser(email, "password", "first", "last");
        User user2 = userService.createUser(email, "password", "first", "two");

        assertNotNull(user);
        assertNull(user2);
    }

    @Test
    public void testGetUserById() {
        String email = "foo@bar.com";
        User user = userService.createUser(email, "password", "first", "name");

        User user2 = userService.getUser(user.getId());

        assertNotNull(user);
        assertEquals(user.getId(), user2.getId());
        assertEquals(email, user2.getEmail());
        assertEquals(user.getFirstName(), user2.getFirstName());
        assertEquals(user.getLastName(), user2.getLastName());
    }

    @Test
    public void testGetUserByEmail() {
        String email = "foo@bar.com";
        userService.createUser(email, "password", "first", "name");

        User user = userService.getUser(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testCreateUserEmailViolation() {
        try {
            userService.createUser("invalid", "password", "first", "name");
            fail();
        } catch (ConstraintViolationException e) {
            // expected
        }
    }
}
