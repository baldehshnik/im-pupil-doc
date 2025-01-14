package im.pupil.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(
        name = "refresh_token",
        schema = "im_pupil",
        indexes = {
                @Index(name = "id", columnList = "id"),
                @Index(name = "user_id", columnList = "user_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "id", columnNames = {"id"}),
                @UniqueConstraint(name = "user_id", columnNames = {"user_id"})
        }
)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotBlank
    @Size(max = 256)
    private String token;

    @NotNull
    private Instant expirationDate;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull @NotBlank @Size(max = 256) String getToken() {
        return token;
    }

    public void setToken(@NotNull @NotBlank @Size(max = 256) String token) {
        this.token = token;
    }

    public @NotNull Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(@NotNull Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public @NotNull User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }
}
