package im.pupil.api.data.repository;

import im.pupil.api.data.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n WHERE n.admin is not null AND n.admin.user.email = :email")
    List<Notification> readNotificationsOfAdmin(
            @Param("email") String email
    );
}



























