package im.pupil.api.repository;

import im.pupil.api.model.Role;
import im.pupil.api.model.UserRole;
import im.pupil.api.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    @Query("SELECT ur.role FROM UserRole ur WHERE ur.user.id = :userId")
    List<Role> findRolesByUserId(@Param("userId") Integer userId);

    @Query("SELECT COUNT(ur) > 0 FROM UserRole ur WHERE ur.user.id = :userId AND ur.role.id = :roleId")
    boolean existsByUserIdAndRoleId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    Optional<UserRole> findByUserIdAndRoleId(Integer userId, Integer id);
}