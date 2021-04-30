package uz.sukhrob.lesson7task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sukhrob.lesson7task1.entity.Comment;
import uz.sukhrob.lesson7task1.entity.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCreatedById(Long createdBy_id);
}
