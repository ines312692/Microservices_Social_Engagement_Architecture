package tn.ensit.soa.engagementSOA.like.services;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tn.ensit.soa.engagementSOA.DTO.UserDto;
import tn.ensit.soa.engagementSOA.like.entities.Like;
import tn.ensit.soa.engagementSOA.like.repositories.LikeRepository;
import tn.ensit.soa.engagementSOA.post.entities.Post;
import tn.ensit.soa.engagementSOA.post.services.PostService;

import java.util.List;


@Service
public class LikeService {
    private final LikeRepository repository;
    private final PostService postService;
    private final RestTemplate restTemplate;

    public LikeService(LikeRepository repository, PostService postService, RestTemplate restTemplate) {
        this.repository = repository;
        this.postService = postService;
        this.restTemplate = restTemplate;
    }

    public Like likePost(Long userId, Long postId) {
        UserDto user = restTemplate.getForObject(
                "http://user-service/users/{id}", UserDto.class, userId
        );
        if (user == null) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        Post post = postService.findById(postId);
        return repository.save(new Like(userId, post));
    }

    public List<Like> getLikesByPost(Long postId) {
        Post post = postService.findById(postId);
        return repository.findByPost(post);
    }
}