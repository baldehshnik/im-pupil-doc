package im.pupil.api.repository;

import im.pupil.api.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findById(int id);
    Optional<Admin> findByEmail(String username);
    List<Admin> findByInstitution_Id(Integer id);
}