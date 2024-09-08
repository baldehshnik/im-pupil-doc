package im.pupil.api.repository;

import im.pupil.api.model.InformationBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformationBlockRepository extends JpaRepository<InformationBlock, Integer> {
}