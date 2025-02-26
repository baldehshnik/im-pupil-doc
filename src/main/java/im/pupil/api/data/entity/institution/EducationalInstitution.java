package im.pupil.api.data.entity.institution;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "educational_institution",
        schema = "im_pupil",
        uniqueConstraints = {
                @UniqueConstraint(name = "name", columnNames = {"name"})
        }
)
public class EducationalInstitution {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 10)
    @NotNull
    @Column(name = "abbreviation", nullable = false, length = 10)
    private String abbreviation;

    @NotNull
    @Column(name = "type", nullable = false)
    private Integer type;

    @Lob
    @Column(name = "address")
    private String address;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;
}