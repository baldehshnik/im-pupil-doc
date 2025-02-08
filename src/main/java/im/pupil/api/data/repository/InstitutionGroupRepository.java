package im.pupil.api.data.repository;

import im.pupil.api.data.entity.group.InstitutionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstitutionGroupRepository extends JpaRepository<InstitutionGroup, Integer> {

    @Query("SELECT g FROM InstitutionGroup g WHERE g.speciality.id = :id")
    List<InstitutionGroup> readInstitutionGroupsBySpecialityId(@Param("id") Integer id);

    @Query("SELECT g FROM InstitutionGroup g WHERE g.speciality.id = :id AND g.name = :name")
    Optional<InstitutionGroup> findInstitutionGroup(
            @Param("id") Integer specialityId,
            @Param("name") String name
    );

    @Query("SELECT g FROM InstitutionGroup g WHERE g.speciality.faculty.institution.id = :id AND g.name LIKE CONCAT('%', :namePart, '%')")
    List<InstitutionGroup> findInstitutionGroupByNamePart(
            @Param("namePart") String namePart,
            @Param("id") Integer institutionId
    );
}


























