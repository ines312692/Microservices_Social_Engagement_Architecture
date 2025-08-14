package tn.ensit.soa.engagementSOA.post.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.ensit.soa.engagementSOA.post.dto.PostDto;
import tn.ensit.soa.engagementSOA.post.entities.Post;
import tn.ensit.soa.engagementSOA.post.services.PostService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PostService postService() {
            return Mockito.mock(PostService.class);
        }
    }

    @Test
    void testCreatePost() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setAuthorId(1L);
        postDto.setContent("This is a new post");

        Post mockPost = new Post(1L, "This is a new post");
        mockPost.setId(100L);
        mockPost.setTimestamp(LocalDateTime.now());

        Mockito.when(postService.createPost(1L, "This is a new post")).thenReturn(mockPost);

        mockMvc.perform(post("/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPostsByUser() throws Exception {
        Long userId = 1L;

        Post mockPost = new Post(userId, "Hello world");
        mockPost.setId(100L);
        mockPost.setTimestamp(LocalDateTime.now());

        Mockito.when(postService.getPostsByAuthor(userId)).thenReturn(List.of(mockPost));

        mockMvc.perform(get("/posts/user/{userId}", userId))
                .andExpect(status().isOk());
    }
}
