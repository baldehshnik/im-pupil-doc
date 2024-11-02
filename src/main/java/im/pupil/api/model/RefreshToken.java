package im.pupil.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "refresh_token", schema = "im_pupil", indexes = {
        @Index(name = "id", columnList = "id"),
        @Index(name = "user_id", columnList = "user_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "id", columnNames = {"id"}),
        @UniqueConstraint(name = "user_id", columnNames = {"user_id"})
})
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @NotNull
    @NotBlank
    @Size(max = 256)
    private String token;

    @Getter
    @NotNull
    private Instant expirationDate;

    @Getter
    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
