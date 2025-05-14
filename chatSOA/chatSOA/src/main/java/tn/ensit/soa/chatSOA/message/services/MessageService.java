package tn.ensit.soa.chatSOA.message.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.ensit.soa.chatSOA.message.dto.MessageDto;
import tn.ensit.soa.chatSOA.message.dto.UserDto;
import tn.ensit.soa.chatSOA.message.entities.Message;
import tn.ensit.soa.chatSOA.message.repositories.MessageRepository;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MessageService {
    private static final Logger LOGGER = Logger.getLogger(MessageService.class.getName());
    private final MessageRepository messageRepository;
    private final RestTemplate restTemplate;

    public MessageService(MessageRepository messageRepository, RestTemplate restTemplate) {
        this.messageRepository = messageRepository;
        this.restTemplate = restTemplate;
    }

    public Message saveMessage(MessageDto dto) {
        UserDto receiver = restTemplate.getForObject(
                "http://user-service/users/{id}", UserDto.class, dto.getReceiverId()
        );
        UserDto sender = restTemplate.getForObject(
                "http://user-service/users/{id}", UserDto.class, dto.getSenderId()
        );
        if (receiver != null && sender != null) {
            Message message = new Message(dto.getChannelId(), dto.getSenderId(), dto.getReceiverId(), dto.getContent());
            return messageRepository.save(message);
        } else {
            String message = "Error while saving Message, user with id %d or %d does not exist."
                    .formatted(dto.getSenderId(), dto.getReceiverId());
            LOGGER.severe(message);
            throw new RuntimeException(message);
        }
    }

    public List<Message> getMessagesBetween(Long senderId, Long receiverId) {
        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}