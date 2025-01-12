package im.pupil.api.model.group;

import im.pupil.api.model.institution.Speciality;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "institution_group",
        schema = "im_pupil",
        indexes = {
                @Index(name = "speciality_id", columnList = "speciality_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "name", columnNames = {"name", "speciality_id"})
        }
)
public class InstitutionGroup {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 32)
    @NotNull
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @NotNull
    @Column(name = "course", nullable = false)
    private Integer course;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

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

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

}