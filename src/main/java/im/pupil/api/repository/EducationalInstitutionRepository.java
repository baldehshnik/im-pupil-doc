package im.pupil.api.repository;

import im.pupil.api.model.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Integer> {
    Optional<EducationalInstitution> findByName(String name);
    Optional<EducationalInstitution> findByAbbreviation(String abbreviation);
}