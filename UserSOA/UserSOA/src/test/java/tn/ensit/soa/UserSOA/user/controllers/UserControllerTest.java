package tn.ensit.soa.UserSOA.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.ensit.soa.UserSOA.user.dto.UserDto;
import tn.ensit.soa.UserSOA.user.entities.User;
import tn.ensit.soa.UserSOA.user.services.UserService;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user1 = new User("ines123");
        user1.setId(1L);
        User user2 = new User("tmimi");
        user2.setId(2L);

        List<User> users = List.of(user1, user2);

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        List<UserDto> expectedDtos = List.of(
                new UserDto(1L, "ines123"),
                new UserDto(2L, "tmimi")
        );

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDtos)));
    }

    @Test
    void testGetOneUser_Found() throws Exception {
        User user = new User("ines123");
        user.setId(1L);

        Mockito.when(userService.getOneUser(1L)).thenReturn(Optional.of(user));

        UserDto expectedDto = new UserDto(1L, "ines123");

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedDto)));
    }

    @Test
    void testGetOneUser_NotFound() throws Exception {
        Mockito.when(userService.getOneUser(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        User userToCreate = new User("newuser");
        // id null before creation
        User createdUser = new User("newuser");
        createdUser.setId(10L);

        Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userToCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.username").value("newuser"));
    }
}
