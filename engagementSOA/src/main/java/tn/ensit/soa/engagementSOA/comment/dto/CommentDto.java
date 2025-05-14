package tn.ensit.soa.engagementSOA.comment.dto;


public class CommentDto {
    private Long commenterId;
    private Long postId;
    private String content;

    public Long getCommenterId() { return commenterId; }
    public void setCommenterId(Long commenterId) { this.commenterId = commenterId; }
    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}