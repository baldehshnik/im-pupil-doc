package im.pupil.api.model.event;

import im.pupil.api.model.InstitutionEvent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalTime;

@Entity
@Table(name = "event_date", schema = "im_pupil", uniqueConstraints = {
        @UniqueConstraint(name = "event_id", columnNames = {"event_id"})
})
public class EventDate {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "event_date")
    private Instant eventDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "meeting_time")
    private LocalTime meetingTime;

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

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(LocalTime meetingTime) {
        this.meetingTime = meetingTime;
    }

    public InstitutionEvent getEvent() {
        return event;
    }

    public void setEvent(InstitutionEvent event) {
        this.event = event;
    }

}