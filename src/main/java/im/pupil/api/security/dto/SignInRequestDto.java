package im.pupil.api.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SignInRequestDto {
    @Size(max = 255, message = "Length of email should be shorter than 255 characters")
    @NotNull
    @NotBlank(message = "Email should be not empty")
    private String email;

    @Size(max = 128)
    @NotNull
    @NotBlank(message = "Password should be not empty")
    private String password;

    public @Size(max = 256) @NotNull @NotBlank(message = "Email should be not empty") String getEmail() {
        return email;
    }

    public void setEmail(@Size(max = 256) @NotNull @NotBlank(message = "Email should be not empty") String email) {
        this.email = email;
    }

    public @Size(max = 128) @NotNull @NotBlank(message = "Password should be not empty") String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 128) @NotNull @NotBlank(message = "Password should be not empty") String password) {
        this.password = password;
    }
}
