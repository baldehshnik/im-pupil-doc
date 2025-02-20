package im.pupil.api.data.repository;

import im.pupil.api.data.entity.schedule.Lesson;
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

    @Query("SELECT l FROM Lesson l WHERE l.schedule.id = :id AND l.weekType = :weekType AND l.dayofweek = :dayOfWeek")
    List<Lesson> findCurrentLessons(
            @Param("id") Integer scheduleId,
            @Param("weekType") Integer weekType,
            @Param("dayOfWeek") Integer dayOfWeek
    );
}
