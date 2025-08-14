package tn.ensit.soa.engagementSOA.post.services;



import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.ensit.soa.engagementSOA.DTO.UserDto;
import tn.ensit.soa.engagementSOA.post.entities.Post;
import tn.ensit.soa.engagementSOA.post.repositories.PostRepository;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;
    private final RestTemplate restTemplate;

    public PostService(PostRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Post createPost(Long authorId, String content) {
        UserDto author = restTemplate.getForObject(
                "http://user-service/users/{id}", UserDto.class, authorId
        );
        if (author == null) {
            throw new RuntimeException("User not found with id: " + authorId);
        }
        Post post = new Post(authorId, content);
        return repository.save(post);
    }

    public List<Post> getPostsByAuthor(Long authorId) {
        return repository.findByAuthorId(authorId);
    }

    public Post findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
    }
}