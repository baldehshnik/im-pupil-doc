package im.pupil.api.repository;

import im.pupil.api.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findById(int id);
    Optional<Admin> findByUserId(int id);

    boolean existsByUserId(int userId);

    @Query("SELECT ad from Admin ad WHERE ad.institution.id = :id")
    List<Admin> findByInstitution_Id(@Param("id") Integer id);
}