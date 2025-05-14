package tn.ensit.soa.UserSOA.friendrequest.dto;

public class FriendRequestDto {
    private Long requesterId;
    private Long receiverId;

    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }
    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
}