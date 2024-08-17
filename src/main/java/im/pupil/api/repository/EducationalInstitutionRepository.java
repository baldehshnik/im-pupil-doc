package im.pupil.api.repository;

import im.pupil.api.model.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Integer> {
    EducationalInstitution findByName(String name);
    EducationalInstitution findByAbbreviation(String abbreviation);
}