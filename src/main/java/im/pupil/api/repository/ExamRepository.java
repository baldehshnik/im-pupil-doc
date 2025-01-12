package im.pupil.api.repository;

import im.pupil.api.model.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

    @Query("SELECT e FROM Exam e WHERE e.group.id = :id")
    List<Exam> readExamsByGroupId(
            @Param("id") Integer groupId
    );

    @Query("SELECT e FROM Exam e WHERE e.name = :name AND e.group.id = :groupId")
    Optional<Exam> readExam(
            @Param("name") String name,
            @Param("groupId") Integer groupId
    );
}






















