package tn.ensit.soa.chatSOA.message.dto;

public class MessageDto {
    private String channelId;
    private Long senderId;
    private Long receiverId;
    private String content;

    public MessageDto() {}

    public MessageDto(String channelId, Long senderId, Long receiverId, String content) {
        this.channelId = channelId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public String getChannelId() { return channelId; }
    public void setChannelId(String channelId) { this.channelId = channelId; }
    public Long getSenderId() { return senderId; }
    public void setSenderId(Long senderId) { this.senderId = senderId; }
    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}