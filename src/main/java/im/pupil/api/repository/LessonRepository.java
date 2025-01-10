package im.pupil.api.repository;

import im.pupil.api.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    @Query("SELECT l FROM Lesson l WHERE l.schedule.id = :id")
    List<Lesson> findAllLessonsByScheduleId(
            @Param("id") Integer scheduleId
    );
}
