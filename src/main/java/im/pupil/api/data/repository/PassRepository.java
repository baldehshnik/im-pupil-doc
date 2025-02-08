package im.pupil.api.data.repository;

import im.pupil.api.data.entity.schedule.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PassRepository extends JpaRepository<Pass, Integer> {

    @Query("SELECT p FROM Pass p WHERE p.date = :date AND p.lesson.id = :lessonId AND p.pupil.id = :groupMemberId")
    Optional<Pass> readPass(
            @Param("date") Instant date,
            @Param("lessonId") Integer lessonId,
            @Param("groupMemberId") Integer groupMemberId
    );

    @Query("SELECT p FROM Pass p WHERE p.pupil.id = :pupilId AND DATE(p.date) = DATE(:date) AND p.status = 1")
    List<Pass> findAllByPupilIdAndDate(@Param("pupilId") Integer pupilId, @Param("date") Instant date);
}























