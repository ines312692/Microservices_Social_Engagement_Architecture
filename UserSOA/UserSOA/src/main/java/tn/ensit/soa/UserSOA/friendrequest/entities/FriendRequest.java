package tn.ensit.soa.UserSOA.friendrequest.entities;

import jakarta.persistence.*;
import tn.ensit.soa.UserSOA.user.entities.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "friend_request")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User requester;
    @ManyToOne
    private User receiver;
    private boolean accepted;
    private LocalDateTime timestamp;

    public FriendRequest() {}

    public FriendRequest(User requester, User receiver) {
        this.requester = requester;
        this.receiver = receiver;
        this.timestamp = LocalDateTime.now();
        this.accepted = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getRequester() { return requester; }
    public void setRequester(User requester) { this.requester = requester; }
    public User getReceiver() { return receiver; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
    public boolean isAccepted() { return accepted; }
    public void setAccepted(boolean accepted) { this.accepted = accepted; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", requesterId=" + (requester != null ? requester.getId() : null) +
                ", receiverId=" + (receiver != null ? receiver.getId() : null) +
                ", accepted=" + accepted +
                '}';
    }
}