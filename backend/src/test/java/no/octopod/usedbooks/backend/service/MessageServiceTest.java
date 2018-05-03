package no.octopod.usedbooks.backend.service;

import no.octopod.usedbooks.backend.AppEntry;
import no.octopod.usedbooks.backend.entity.Message;
import no.octopod.usedbooks.backend.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppEntry.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MessageServiceTest extends ServiceTestBase {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateMessage() {
        User user1 = makeUser("user1@bar.com");
        User user2 = makeUser("user2@bar.com");

        Message msg = messageService.createMessage(user1.getId(), user2.getId(), "message");
        assertNotNull(msg);
        assertNotNull(msg.getId());
    }

    @Test
    public void testMessagesSentByUser() {
        User user1 = makeUser("user1@bar.com");
        User user2 = makeUser("user2@bar.com");

        messageService.createMessage(user1.getId(), user2.getId(), "1");
        messageService.createMessage(user1.getId(), user2.getId(), "2");
        messageService.createMessage(user1.getId(), user2.getId(), "3");
        messageService.createMessage(user1.getId(), user2.getId(), "4");

        messageService.createMessage(user2.getId(), user1.getId(), "5");

        List<Message> messagesSentByUser1 = messageService.getMessagesSent(user1.getId());
        List<Message> messagesSentByUser2 = messageService.getMessagesSent(user2.getId());

        assertEquals(4, messagesSentByUser1.size());
        assertEquals(1, messagesSentByUser2.size());
    }

    @Test
    public void testMessagesReceivedByUser() {
        User user1 = makeUser("user1@bar.com");
        User user2 = makeUser("user2@bar.com");

        messageService.createMessage(user1.getId(), user2.getId(), "1");
        messageService.createMessage(user1.getId(), user2.getId(), "2");
        messageService.createMessage(user1.getId(), user2.getId(), "3");
        messageService.createMessage(user2.getId(), user1.getId(), "4");
        messageService.createMessage(user2.getId(), user1.getId(), "5");

        List<Message> messagesReceivedByUser2 = messageService.getMessagesReceived(user2.getId());
        List<Message> messagesReceivedByUser1 = messageService.getMessagesReceived(user1.getId());

        assertEquals(3, messagesReceivedByUser2.size());
        assertEquals(2, messagesReceivedByUser1.size());
    }

    @Test
    public void testMessagesBetweenUsers() {
        User user1 = makeUser("user1@bar.com");
        User user2 = makeUser("user2@bar.com");
        User user3 = makeUser("user3@bar.com");

        messageService.createMessage(user1.getId(), user2.getId(), "1 -> 2");
        messageService.createMessage(user2.getId(), user1.getId(), "2 -> 1");
        messageService.createMessage(user3.getId(), user1.getId(), "3 -> 1");

        List<Message> messagesBetweenOneAndTwo = messageService.getMessagesBetween(user1.getId(), user2.getId());
        assertEquals(2, messagesBetweenOneAndTwo.size());
        messagesBetweenOneAndTwo = messageService.getMessagesBetween(user2.getId(), user1.getId());
        assertEquals(2, messagesBetweenOneAndTwo.size());

        List<Message> messagesBetweenThreeAndOne = messageService.getMessagesBetween(user3.getId(), user1.getId());
        List<Message> messagesBetweenThreeAndTwo = messageService.getMessagesBetween(user3.getId(), user2.getId());

        assertEquals(1, messagesBetweenThreeAndOne.size());
        assertEquals(0, messagesBetweenThreeAndTwo.size());
    }

    @Test
    public void testMessagesBetweenUsersSortedChronologically() {
        User user1 = makeUser("user1@bar.com");
        User user2 = makeUser("user2@bar.com");


        String msg1 = "Hello! This is first message!";
        String msg2 = "Okay?";
        String msg3 = "Give me book.";
        String msg4 = "Don't ever send me a message again.";

        messageService.createMessage(user1.getId(), user2.getId(), msg1);
        messageService.createMessage(user2.getId(), user1.getId(), msg2);
        messageService.createMessage(user1.getId(), user2.getId(), msg3);
        messageService.createMessage(user2.getId(), user1.getId(), msg4);

        List<Message> messages = messageService.getMessagesBetween(user1.getId(), user2.getId());
        assertEquals(4, messages.size());

        assertEquals(user1.getId(), messages.get(0).getSender());
        assertEquals(user2.getId(), messages.get(0).getRecipient());
        assertEquals(msg1, messages.get(0).getContent());
        assertEquals(msg2, messages.get(1).getContent());
        assertEquals(msg3, messages.get(2).getContent());
        assertEquals(msg4, messages.get(3).getContent());
    }

    private User makeUser(String email) {
        return userService.createUser(email, "password", "first", "second");
    }
}
