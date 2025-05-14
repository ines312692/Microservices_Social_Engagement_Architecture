package tn.ensit.soa.UserSOA.user.services;



import org.springframework.stereotype.Service;
import tn.ensit.soa.UserSOA.profile.entities.Profile;
import tn.ensit.soa.UserSOA.profile.services.ProfileService;
import tn.ensit.soa.UserSOA.user.entities.User;
import tn.ensit.soa.UserSOA.user.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileService profileService;

    public UserService(UserRepository userRepository, ProfileService profileService) {
        this.userRepository = userRepository;
        this.profileService = profileService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getOneUser(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        User createdUser = userRepository.save(user);
        Profile profile = new Profile(createdUser, "");
        profileService.createProfile(profile);
        return createdUser;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}