package tn.ensit.soa.engagementSOA.like.entities;



import jakarta.persistence.*;
import tn.ensit.soa.engagementSOA.post.entities.Post;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    private LocalDateTime timestamp;

    protected Like() {}

    public Like(Long userId, Post post) {
        this.userId = userId;
        this.post = post;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + (post != null ? post.getId() : null) +
                '}';
    }
}