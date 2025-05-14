package tn.ensit.soa.UserSOA.profile.services;


import org.springframework.stereotype.Service;
import tn.ensit.soa.UserSOA.profile.entities.Profile;
import tn.ensit.soa.UserSOA.profile.repositories.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }

    public List<Profile> getAllProfiles() {
        return repository.findAll();
    }

    public Optional<Profile> getOneProfile(Long id) {
        return repository.findById(id);
    }

    public Profile createProfile(Profile profile) {
        return repository.save(profile);
    }
}