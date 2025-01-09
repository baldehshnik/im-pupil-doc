package im.pupil.api.model.about;

import im.pupil.api.model.EducationalInstitution;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(
        name = "about",
        schema = "im_pupil",
        indexes = {
                @Index(name = "institution_id", columnList = "institution_id")
        }
)
public class About {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon", columnDefinition = "TEXT")
    private String icon;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public @NotNull EducationalInstitution getInstitution() {
        return institution;
    }

    public void setInstitution(@NotNull EducationalInstitution institution) {
        this.institution = institution;
    }
}






















