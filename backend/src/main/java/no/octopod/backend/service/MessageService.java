package no.octopod.backend.service;

import no.octopod.backend.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired
    private EntityManager em;

    public Message createMessage(Long senderId, Long recipientId, String content) {
        Message message = new Message();
        message.setSender(senderId);
        message.setRecipient(recipientId);
        message.setContent(content);

        em.persist(message);
        return message;
    }

    public List<Message> getMessagesSent(Long senderId) {
        TypedQuery<Message> query =
                em.createQuery("select m from Message m where m.sender=?1", Message.class);
        query.setParameter(1, senderId);
        return query.getResultList();
    }

    public List<Message> getMessagesReceived(Long recipientId) {
        TypedQuery<Message> query =
                em.createQuery("select m from Message m where m.recipient=?1", Message.class);
        query.setParameter(1, recipientId);
        return query.getResultList();
    }

    public List<Message> getMessagesBetween(Long user1, Long user2) {
        TypedQuery<Message> query =
                em.createQuery("select m from Message m where m.sender=?1 and m.recipient=?2 or m.sender=?2 and m.recipient=?1",
                        Message.class);
        query.setParameter(1, user1);
        query.setParameter(2, user2);
        return query.getResultList();
    }
}
