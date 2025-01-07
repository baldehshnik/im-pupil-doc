package im.pupil.api.repository;

import im.pupil.api.model.EducationalInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducationalInstitutionRepository extends JpaRepository<EducationalInstitution, Integer> {
    Optional<EducationalInstitution> findByName(String name);
    Optional<EducationalInstitution> findByAbbreviation(String abbreviation);

    @Query(value = "SELECT * FROM educational_institution WHERE name LIKE %:namePart% LIMIT 8", nativeQuery = true)
    List<EducationalInstitution> findTop8ByNameContaining(@Param("namePart") String namePart);


}













