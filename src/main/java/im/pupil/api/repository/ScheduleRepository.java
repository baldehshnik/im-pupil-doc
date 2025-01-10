package im.pupil.api.repository;

import im.pupil.api.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("SELECT s FROM Schedule s WHERE s.group.id = :groupId")
    List<Schedule> readSchedulesByGroupId(
            @Param("groupId") Integer groupId
    );
}
