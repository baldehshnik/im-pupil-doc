package im.pupil.api.data.repository;

import im.pupil.api.data.entity.about.About;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AboutRepository extends JpaRepository<About, Integer> {

    @Query("SELECT a FROM About a WHERE a.institution.id = :id")
    List<About> readAboutBlocks(@Param("id") Integer institutionId);
}
