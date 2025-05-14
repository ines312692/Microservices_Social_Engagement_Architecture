package tn.ensit.soa.engagementSOA.like.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.ensit.soa.engagementSOA.like.dto.LikeDto;
import tn.ensit.soa.engagementSOA.like.entities.Like;
import tn.ensit.soa.engagementSOA.like.services.LikeService;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/add")
    public ResponseEntity<Like> addLike(@RequestBody LikeDto likeDto) {
        Like like = likeService.likePost(likeDto.getUserId(), likeDto.getPostId());
        return ResponseEntity.ok(like);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Like>> getLikesByPost(@PathVariable Long postId) {
        List<Like> likes = likeService.getLikesByPost(postId);
        return ResponseEntity.ok(likes);
    }
}