package tn.ensit.soa.UserSOA.profile.entities;

import jakarta.persistence.*;
import tn.ensit.soa.UserSOA.user.entities.User;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bio;
    @OneToOne
    private User user;

    protected Profile() {}

    public Profile(String bio) {
        this.bio = bio;
    }

    public Profile(User user, String bio) {
        this.bio = bio;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", bio='" + bio + '\'' +
                ", userId='" + (user != null ? user.getId() : null) + '\'' +
                '}';
    }
}