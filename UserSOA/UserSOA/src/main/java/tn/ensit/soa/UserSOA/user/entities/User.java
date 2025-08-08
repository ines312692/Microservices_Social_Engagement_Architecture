package tn.ensit.soa.UserSOA.user.entities;

import jakarta.persistence.*;
import tn.ensit.soa.UserSOA.friendrequest.entities.FriendRequest;
import tn.ensit.soa.UserSOA.profile.entities.Profile;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
    private List<FriendRequest> sentRequests = new ArrayList<>();
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<FriendRequest> receivedRequests = new ArrayList<>();

    protected User() {}


    public User(String username) {
        this.username = username;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
    public List<FriendRequest> getSentRequests() { return sentRequests; }
    public void setSentRequests(List<FriendRequest> sentRequests) { this.sentRequests = sentRequests; }
    public List<FriendRequest> getReceivedRequests() { return receivedRequests; }
    public void setReceivedRequests(List<FriendRequest> receivedRequests) { this.receivedRequests = receivedRequests; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}