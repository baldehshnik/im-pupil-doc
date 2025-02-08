package im.pupil.api.data.repository;

import im.pupil.api.data.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query("SELECT a FROM Admin a WHERE a.user.id = :userId")
    Optional<Admin> findByUserId(
            @Param("userId") Integer id
    );

    boolean existsByUserId(int userId);

    @Query("SELECT ad from Admin ad WHERE ad.institution.id = :id AND ad.status = 1")
    List<Admin> findByInstitution_Id(@Param("id") Integer id);

    @Query("SELECT a FROM Admin a WHERE a.user.email = :email AND a.status = 1")
    Optional<Admin> findRegisteredAdminByEmail(
            @Param("email") String email
    );

    @Query("SELECT a FROM Admin a WHERE a.user.email = :email")
    Optional<Admin> findAdminByEmail(
            @Param("email") String email
    );

    @Query("SELECT ad from Admin ad WHERE ad.institution.id = :id AND ad.status = -1")
    List<Admin> findNotConfirmedAdmins(
            @Param("id") Integer institutionId
    );

    @Query("SELECT a from Admin a WHERE a.institution.id = :id AND a.status = 1 AND a.accessMode >= 2")
    List<Admin> findByInstitutionIdAndTeacherAccess(
            @Param("id") Integer institutionId
    );
}
















