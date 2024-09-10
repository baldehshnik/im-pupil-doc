package im.pupil.api.repository;

import im.pupil.api.model.Practice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PracticeRepository extends JpaRepository<Practice, Integer> {
    Optional<Practice> findPracticeById(Integer id);
    List<Practice> findByInstitution_Id(Integer id);
}