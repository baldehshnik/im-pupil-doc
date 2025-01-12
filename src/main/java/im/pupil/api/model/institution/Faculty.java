package im.pupil.api.model.institution;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "faculty",
        schema = "im_pupil",
        indexes = {
                @Index(name = "institution_id", columnList = "institution_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "name", columnNames = {"name", "institution_id"})
        }
)
public class Faculty {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 256)
    @NotNull
    @Column(name = "name", nullable = false, length = 256)
    private String name;

    @Size(max = 10)
    @NotNull
    @Column(name = "abbreviation", nullable = false, length = 10)
    private String abbreviation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "institution_id", nullable = false)
    private EducationalInstitution institution;

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public EducationalInstitution getInstitution() {
        return institution;
    }

    public void setInstitution(EducationalInstitution institution) {
        this.institution = institution;
    }

}










