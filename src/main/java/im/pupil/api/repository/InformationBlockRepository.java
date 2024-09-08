package im.pupil.api.repository;

import im.pupil.api.model.InformationBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InformationBlockRepository extends JpaRepository<InformationBlock, Integer> {
    Optional<InformationBlock> findInformationBlockById(Integer id);
    Optional<InformationBlock> findInformationBlockByTitleAndPractice_Id(String name, Integer practiceId);
}