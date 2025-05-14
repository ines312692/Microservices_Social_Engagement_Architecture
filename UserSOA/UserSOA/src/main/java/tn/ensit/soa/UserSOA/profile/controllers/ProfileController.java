package tn.ensit.soa.UserSOA.profile.controllers;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.ensit.soa.UserSOA.profile.entities.Profile;
import tn.ensit.soa.UserSOA.profile.services.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok(service.getAllProfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getOneProfile(@PathVariable Long id) {
        return service.getOneProfile(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
