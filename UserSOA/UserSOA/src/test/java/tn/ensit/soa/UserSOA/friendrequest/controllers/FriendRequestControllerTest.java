package tn.ensit.soa.UserSOA.friendrequest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.ensit.soa.UserSOA.friendrequest.dto.FriendRequestDto;
import tn.ensit.soa.UserSOA.friendrequest.entities.FriendRequest;
import tn.ensit.soa.UserSOA.friendrequest.services.FriendRequestService;
import tn.ensit.soa.UserSOA.user.entities.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FriendRequestController.class)
class FriendRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public FriendRequestService friendRequestService() {
            return Mockito.mock(FriendRequestService.class);
        }
    }

    @Test
    void testSendRequest() throws Exception {
        FriendRequestDto dto = new FriendRequestDto();
        dto.setRequesterId(1L);
        dto.setReceiverId(2L);

        User requester = new User("ines123");
        requester.setId(1L);
        User receiver = new User("tmimi");
        receiver.setId(2L);

        FriendRequest mockRequest = new FriendRequest(requester, receiver);
        mockRequest.setId(5L);
        mockRequest.setTimestamp(LocalDateTime.now());

        Mockito.when(friendRequestService.sendFriendRequest(1L, 2L)).thenReturn(mockRequest);

        mockMvc.perform(post("/friendRequests/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPendingRequests() throws Exception {
        Long userId = 2L;

        User receiver = new User("ines");
        receiver.setId(userId);

        FriendRequest mockRequest = new FriendRequest(new User(){{
            setId(1L);
        }}, receiver);
        mockRequest.setId(6L);
        mockRequest.setTimestamp(LocalDateTime.now());

        Mockito.when(friendRequestService.getPendingRequests(userId))
                .thenReturn(List.of(mockRequest));

        mockMvc.perform(get("/friendRequests/pending/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    void testAcceptRequest() throws Exception {
        Long requestId = 6L;

        FriendRequest mockRequest = new FriendRequest(
                new User(){ { setId(1L); } },
                new User(){ { setId(2L); } }
        );
        mockRequest.setId(requestId);
        mockRequest.setAccepted(true);
        mockRequest.setTimestamp(LocalDateTime.now());

        Mockito.when(friendRequestService.acceptRequest(requestId)).thenReturn(mockRequest);

        mockMvc.perform(post("/friendRequests/accept/{requestId}", requestId))
                .andExpect(status().isOk());
    }
}
