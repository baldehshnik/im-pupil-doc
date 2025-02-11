package im.pupil.api.data.repository;

import im.pupil.api.data.entity.ServiceDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceDataEntity, Long> {

    @Query("SELECT s FROM ServiceDataEntity s WHERE s.enabled = true")
    List<ServiceDataEntity> readAllEnabled();
}


























