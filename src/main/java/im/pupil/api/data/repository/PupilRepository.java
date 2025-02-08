package im.pupil.api.data.repository;

import im.pupil.api.data.entity.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PupilRepository extends JpaRepository<Pupil, Integer> {

    Optional<Pupil> findById(int id);
    Optional<Pupil> findByUserId(int userId);

    @Query("SELECT p FROM Pupil p WHERE p.code = :code AND p.institution.id = :id AND p.status = -1")
    Optional<Pupil> findNotRegisteredByCodeAndInstitutionId(
            @Param("code") String code,
            @Param("id") Integer institutionId
    );

    @Query("SELECT p FROM Pupil p WHERE p.code = :code AND p.institution.id = :id AND p.status = 1")
    Optional<Pupil> findRegisteredByCodeAndInstitutionId(
            @Param("code") String code,
            @Param("id") Integer institutionId
    );

    @Query("SELECT p FROM Pupil p WHERE p.user.email = :email")
    Optional<Pupil> findByEmail(
        @Param("email") String email
    );

    @Query("SELECT p FROM Pupil p WHERE p.id = :id AND p.status = 1")
    Optional<Pupil> findRegisteredById(
            @Param("id") Integer id
    );

    @Query("SELECT p FROM Pupil p WHERE p.institution.id = :id AND p.status = -1")
    List<Pupil> findNotRegisteredByInstitutionId(
            @Param("id") Integer id
    );


}















