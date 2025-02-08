package im.pupil.api.data.repository;

import im.pupil.api.data.entity.institution.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    @Query("SELECT f FROM Faculty f WHERE f.institution.id = :id")
    List<Faculty> readFacultiesByInstitutionId(@Param("id") Integer id);
}























