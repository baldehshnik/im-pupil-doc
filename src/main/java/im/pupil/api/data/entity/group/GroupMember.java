package im.pupil.api.data.entity.group;

import im.pupil.api.data.entity.Pupil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "group_member",
        schema = "im_pupil",
        indexes = {
                @Index(name = "pupil_id", columnList = "pupil_id"),
                @Index(name = "group_id", columnList = "group_id")
        },
        uniqueConstraints = @UniqueConstraint(columnNames = {"code", "institution_id"})
)
public class GroupMember {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 32)
    @NotNull
    @Column(name = "firstname", nullable = false, length = 32)
    private String firstname;

    @Size(max = 32)
    @NotNull
    @Column(name = "lastname", nullable = false, length = 32)
    private String lastname;

    @Size(max = 32)
    @Column(name = "patronymic", length = 32)
    private String patronymic;

    @Size(max = 16)
    @NotNull
    @Column(name = "code", nullable = false, length = 16)
    private String code;

    @NotNull
    @Column(name = "isPrefect", nullable = false, columnDefinition = "boolean default false")
    private Boolean isPrefect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pupil_id")
    private Pupil pupil;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "group_id", nullable = false)
    private InstitutionGroup group;

    public @NotNull Boolean getPrefect() {
        return isPrefect;
    }

    public void setPrefect(@NotNull Boolean prefect) {
        isPrefect = prefect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    public InstitutionGroup getGroup() {
        return group;
    }

    public void setGroup(InstitutionGroup group) {
        this.group = group;
    }

    @Transient
    public Integer getInstitutionId() {
        return group != null && group.getSpeciality() != null && group.getSpeciality().getFaculty() != null
                ? group.getSpeciality().getFaculty().getInstitution().getId()
                : null;
    }
}
















