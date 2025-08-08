package tn.ensit.soa.engagementSOA.like.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.ensit.soa.engagementSOA.like.dto.LikeDto;
import tn.ensit.soa.engagementSOA.like.entities.Like;
import tn.ensit.soa.engagementSOA.like.services.LikeService;
import tn.ensit.soa.engagementSOA.post.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public LikeService likeService() {
            return Mockito.mock(LikeService.class);
        }
    }

    @Test
    void testAddLike() throws Exception {
        LikeDto likeDto = new LikeDto();
        likeDto.setUserId(1L);
        likeDto.setPostId(100L);

        Post post = new Post();
        post.setId(100L);

        Like mockLike = new Like(1L, post);
        mockLike.setId(1L);
        mockLike.setTimestamp(LocalDateTime.now());

        Mockito.when(likeService.likePost(1L, 100L)).thenReturn(mockLike);

        mockMvc.perform(post("/likes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(likeDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetLikesByPost() throws Exception {
        Long postId = 100L;

        Post post = new Post();
        post.setId(postId);

        Like mockLike = new Like(1L, post);
        mockLike.setId(1L);
        mockLike.setTimestamp(LocalDateTime.now());

        Mockito.when(likeService.getLikesByPost(postId)).thenReturn(List.of(mockLike));

        mockMvc.perform(get("/likes/post/{postId}", postId))
                .andExpect(status().isOk());
    }
}
