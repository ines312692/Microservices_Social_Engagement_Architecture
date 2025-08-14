package tn.ensit.soa.engagementSOA.comment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.ensit.soa.engagementSOA.comment.dto.CommentDto;
import tn.ensit.soa.engagementSOA.comment.entities.Comment;
import tn.ensit.soa.engagementSOA.comment.services.CommentService;
import tn.ensit.soa.engagementSOA.post.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CommentService commentService() {
            return Mockito.mock(CommentService.class);
        }
    }

    @Test
    void testAddComment() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommenterId(1L);
        commentDto.setPostId(10L);
        commentDto.setContent("This is a test comment");

        Post post = new Post();
        post.setId(10L);

        Comment mockComment = new Comment(1L, post, "This is a test comment");
        mockComment.setId(100L);
        mockComment.setTimestamp(LocalDateTime.now());

        Mockito.when(commentService.addComment(1L, 10L, "This is a test comment")).thenReturn(mockComment);

        mockMvc.perform(post("/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCommentsByPost() throws Exception {
        Long postId = 10L;
        Post post = new Post();
        post.setId(postId);

        Comment comment = new Comment(1L, post, "Sample comment");
        comment.setId(1L);
        comment.setTimestamp(LocalDateTime.now());

        Mockito.when(commentService.getCommentsByPost(postId)).thenReturn(List.of(comment));

        mockMvc.perform(get("/comments/post/{postId}", postId))
                .andExpect(status().isOk());
    }
}
