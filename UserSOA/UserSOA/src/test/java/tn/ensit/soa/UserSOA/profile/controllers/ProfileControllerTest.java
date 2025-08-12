package tn.ensit.soa.UserSOA.profile.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import tn.ensit.soa.UserSOA.profile.entities.Profile;
import tn.ensit.soa.UserSOA.profile.services.ProfileService;
import tn.ensit.soa.UserSOA.user.entities.User;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class Config {
        @Bean
        public ProfileService profileService() {
            return Mockito.mock(ProfileService.class);
        }
    }

    @Test
    void testGetAllProfiles() throws Exception {
        User user = new User("ines123");
        user.setId(1L);
        Profile profile = new Profile(user, "bio de test");
        profile.setId(10L);

        List<Profile> profiles = List.of(profile);

        Mockito.when(profileService.getAllProfiles()).thenReturn(profiles);

        mockMvc.perform(get("/profiles"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(profiles)));
    }

    @Test
    void testGetOneProfile_Found() throws Exception {
        User user = new User("ines123");
        user.setId(1L);
        Profile profile = new Profile(user, "bio trouvé");
        profile.setId(20L);

        Mockito.when(profileService.getOneProfile(20L)).thenReturn(Optional.of(profile));

        mockMvc.perform(get("/profiles/20"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(profile)));
    }

    @Test
    void testGetOneProfile_NotFound() throws Exception {
        Mockito.when(profileService.getOneProfile(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/profiles/99"))
                .andExpect(status().isNotFound());
    }
}
