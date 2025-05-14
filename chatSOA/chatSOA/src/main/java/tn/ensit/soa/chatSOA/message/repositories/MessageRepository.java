package tn.ensit.soa.chatSOA.message.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.ensit.soa.chatSOA.message.entities.Message;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}