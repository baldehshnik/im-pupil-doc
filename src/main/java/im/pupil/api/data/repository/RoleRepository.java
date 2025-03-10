package im.pupil.api.data.repository;

import im.pupil.api.data.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String role);
    Optional<Role> findById(int roleId);
}