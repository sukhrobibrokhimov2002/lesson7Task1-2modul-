package uz.sukhrob.lesson7task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sukhrob.lesson7task1.entity.Post;
import uz.sukhrob.lesson7task1.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {
}
