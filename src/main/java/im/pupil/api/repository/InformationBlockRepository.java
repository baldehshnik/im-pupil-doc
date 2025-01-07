package im.pupil.api.repository;

import im.pupil.api.model.InformationBlock;
import im.pupil.api.model.Relocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InformationBlockRepository extends JpaRepository<InformationBlock, Integer> {
    Optional<InformationBlock> findInformationBlockById(Integer id);
    Optional<InformationBlock> findInformationBlockByTitleAndPractice_Id(String name, Integer practiceId);

    @Query("SELECT inf FROM InformationBlock inf WHERE inf.practice.id = :practiceId")
    List<InformationBlock> findAllByPracticeId(@Param("practiceId") int practiceId);
}