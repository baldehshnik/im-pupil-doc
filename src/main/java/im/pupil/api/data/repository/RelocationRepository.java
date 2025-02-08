package im.pupil.api.data.repository;

import im.pupil.api.data.entity.Relocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RelocationRepository extends JpaRepository<Relocation, Integer> {
    Optional<Relocation> findById(int id);
    Optional<Relocation> findByNameAndPractice_Id(String name, int practiceId);

    @Query("SELECT re FROM Relocation re WHERE re.practice.id = :practiceId")
    List<Relocation> findAllByPracticeId(@Param("practiceId") int practiceId);
}