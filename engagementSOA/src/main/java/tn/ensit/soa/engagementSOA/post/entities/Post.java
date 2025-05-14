package tn.ensit.soa.engagementSOA.post.entities;



import jakarta.persistence.*;
import tn.ensit.soa.engagementSOA.comment.entities.Comment;
import tn.ensit.soa.engagementSOA.like.entities.Like;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long authorId;
    private String content;
    private LocalDateTime timestamp;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    public Post() {}

    public Post(Long authorId, String content) {
        this.authorId = authorId;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public List<Like> getLikes() { return likes; }
    public void setLikes(List<Like> likes) { this.likes = likes; }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", content='" + content + '\'' +
                '}';
    }
}