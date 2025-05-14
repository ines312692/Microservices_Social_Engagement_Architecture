package tn.ensit.soa.UserSOA.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.ensit.soa.UserSOA.profile.entities.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {}