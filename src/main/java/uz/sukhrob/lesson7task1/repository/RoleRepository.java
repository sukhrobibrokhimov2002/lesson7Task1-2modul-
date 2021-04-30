package uz.sukhrob.lesson7task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sukhrob.lesson7task1.entity.Post;
import uz.sukhrob.lesson7task1.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
