package im.pupil.api.data.repository;

import im.pupil.api.data.entity.news.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Integer> {

    @Query("SELECT g FROM Guide g WHERE g.news.id = :newsId")
    List<Guide> findByNewsId(
            @Param("newsId") Integer newsId
    );
}
