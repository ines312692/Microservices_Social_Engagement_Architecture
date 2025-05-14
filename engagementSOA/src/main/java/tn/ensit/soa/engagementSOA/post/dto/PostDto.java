package tn.ensit.soa.engagementSOA.post.dto;



public class PostDto {
    private Long authorId;
    private String content;

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}