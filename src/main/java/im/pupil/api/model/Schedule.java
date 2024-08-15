package im.pupil.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "schedule", schema = "im_pupil", indexes = {
        @Index(name = "group_id", columnList = "group_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "name", columnNames = {"name", "group_id"})
})
public class Schedule {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 32)
    @NotNull
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @NotNull
    @Column(name = "finish_date", nullable = false)
    private Instant finishDate;

    @NotNull
    @Column(name = "start_type", nullable = false)
    private Integer startType;

    @NotNull
    @ColumnDefault("current_timestamp()")
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "type", nullable = false)
    private Integer type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "group_id", nullable = false)
    private InstitutionGroup group;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Instant finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getStartType() {
        return startType;
    }

    public void setStartType(Integer startType) {
        this.startType = startType;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public InstitutionGroup getGroup() {
        return group;
    }

    public void setGroup(InstitutionGroup group) {
        this.group = group;
    }

}