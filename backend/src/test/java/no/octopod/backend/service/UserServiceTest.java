package no.octopod.backend.service;

import no.octopod.backend.AppEntry;
import no.octopod.backend.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

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
        Long id = userService.createUser("foo@bar.com", "password",
                "first", "last");
        assertNotNull(id);
    }

    @Test
    public void testCreateDuplicateUser() {
        String email = "foo@bar.com";

        Long id = userService.createUser(email, "password", "first", "last");
        Long id2 = userService.createUser(email, "password", "first", "two");

        assertNotEquals(UserService.EMAIL_ALREADY_IN_USE, id);
        assertEquals(UserService.EMAIL_ALREADY_IN_USE, id2);
    }

    @Test
    public void testGetUserById() {
        String email = "foo@bar.com";
        Long id = userService.createUser(email, "password", "first", "name");

        User user = userService.getUser(id);

        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(email, user.getEmail());
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
