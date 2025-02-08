package im.pupil.api.data.entity.event;

import im.pupil.api.data.entity.InstitutionEvent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "event_reward", schema = "im_pupil", uniqueConstraints = {
        @UniqueConstraint(name = "event_id", columnNames = {"event_id"})
})
public class EventReward {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "public_hours")
    private Double publicHours;

    @Column(name = "work_hours")
    private Double workHours;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", nullable = false)
    private InstitutionEvent event;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPublicHours() {
        return publicHours;
    }

    public void setPublicHours(Double publicHours) {
        this.publicHours = publicHours;
    }

    public Double getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Double workHours) {
        this.workHours = workHours;
    }

    public InstitutionEvent getEvent() {
        return event;
    }

    public void setEvent(InstitutionEvent event) {
        this.event = event;
    }

}