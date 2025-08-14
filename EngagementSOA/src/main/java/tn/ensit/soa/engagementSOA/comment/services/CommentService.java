package tn.ensit.soa.engagementSOA.comment.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.ensit.soa.engagementSOA.DTO.UserDto;
import tn.ensit.soa.engagementSOA.comment.entities.Comment;
import tn.ensit.soa.engagementSOA.comment.repositories.CommentRepository;
import tn.ensit.soa.engagementSOA.post.entities.Post;
import tn.ensit.soa.engagementSOA.post.services.PostService;

import java.util.List;


@Service
public class CommentService {
    private final CommentRepository repository;
    private final PostService postService;
    private final RestTemplate restTemplate;

    public CommentService(CommentRepository repository, PostService postService, RestTemplate restTemplate) {
        this.repository = repository;
        this.postService = postService;
        this.restTemplate = restTemplate;
    }

    public Comment addComment(Long commenterId, Long postId, String content) {
        UserDto commenter = restTemplate.getForObject(
                "http://user-service/users/{id}", UserDto.class, commenterId
        );
        if (commenter == null) {
            throw new RuntimeException("User not found with id: " + commenterId);
        }
        Post post = postService.findById(postId);
        return repository.save(new Comment(commenterId, post, content));
    }

    public List<Comment> getCommentsByPost(Long postId) {
        Post post = postService.findById(postId);
        return repository.findByPost(post);
    }
}
