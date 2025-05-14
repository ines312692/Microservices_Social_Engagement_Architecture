package tn.ensit.soa.UserSOA.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.ensit.soa.UserSOA.user.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {}