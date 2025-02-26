package im.pupil.api.data.entity.about;

import im.pupil.api.data.entity.institution.EducationalInstitution;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
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
}






















