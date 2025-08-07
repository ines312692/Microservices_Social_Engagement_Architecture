package tn.ensit.soa.chatSOA.message.controllers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.ensit.soa.chatSOA.message.dto.MessageDto;
import tn.ensit.soa.chatSOA.message.entities.Message;
import tn.ensit.soa.chatSOA.message.services.MessageService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageService messageService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public MessageService messageService() {
            return Mockito.mock(MessageService.class);
        }
    }

    @Test
    void testCreateMessage() throws Exception {
        Message message = new Message("channel1", 1L, 2L, "Hello, World!");
        Mockito.when(messageService.saveMessage(Mockito.any(MessageDto.class))).thenReturn(message);

        mockMvc.perform(post("/messages/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"channelId\":\"channel1\",\"senderId\":1,\"receiverId\":2,\"content\":\"Hello, World!\"}"))
                .andExpect(status().isOk());
    }
}

