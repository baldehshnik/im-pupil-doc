package im.pupil.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "pass", schema = "im_pupil", indexes = {
        @Index(name = "pupil_id", columnList = "pupil_id"),
        @Index(name = "lesson_id", columnList = "lesson_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "date", columnNames = {"date", "lesson_id", "pupil_id"})
})
public class Pass {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "date", nullable = false)
    private Instant date;

    @NotNull
    @ColumnDefault("-1")
    @Column(name = "status", nullable = false)
    private Integer status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pupil_id", nullable = false)
    private GroupMember pupil;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public GroupMember getPupil() {
        return pupil;
    }

    public void setPupil(GroupMember pupil) {
        this.pupil = pupil;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

}