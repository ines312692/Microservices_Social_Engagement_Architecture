package tn.ensit.soa.engagementSOA.comment.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.ensit.soa.engagementSOA.comment.entities.Comment;
import tn.ensit.soa.engagementSOA.post.entities.Post;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long > {
List<Comment> findByPost(Post post);
}