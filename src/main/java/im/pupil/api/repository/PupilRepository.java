package im.pupil.api.repository;

import im.pupil.api.model.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PupilRepository extends JpaRepository<Pupil, Integer> {
    Optional<Pupil> findById(int id);
    Optional<Pupil> findByEmail(String email);
    Optional<Pupil> findByCode(String code);
    List<Pupil> findByInstitution_Id(int id);
}