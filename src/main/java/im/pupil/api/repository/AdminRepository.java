package im.pupil.api.repository;

import im.pupil.api.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findById(int id);
    Optional<Admin> findByUserId(int id);

    boolean existsByUserId(int userId);

    List<Admin> findByInstitution_Id(Integer id);
}