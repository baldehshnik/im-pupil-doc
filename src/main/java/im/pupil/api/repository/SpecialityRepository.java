package im.pupil.api.repository;

import im.pupil.api.model.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    @Query("SELECT s FROM Speciality s WHERE s.faculty.id = :id")
    List<Speciality> readSpecialityByFacultyId(@Param("id") Integer id);
}
