package tn.ensit.soa.engagementSOA.like.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.ensit.soa.engagementSOA.like.entities.Like;
import tn.ensit.soa.engagementSOA.post.entities.Post;

import java.util.List;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPost(Post post);
}