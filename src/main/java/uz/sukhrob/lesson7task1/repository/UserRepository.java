package uz.sukhrob.lesson7task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sukhrob.lesson7task1.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
