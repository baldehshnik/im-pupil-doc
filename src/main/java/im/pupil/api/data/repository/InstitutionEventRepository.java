package im.pupil.api.data.repository;

import im.pupil.api.data.entity.InstitutionEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstitutionEventRepository extends JpaRepository<InstitutionEvent, Integer> {
    List<InstitutionEvent> findByInstitutionId(Integer institutionId);
}






















