package im.pupil.api.security.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SignUpPupilRequestDto {
    @Size(max = 32, message = "Length of firstname should be shorter than 32 characters")
    @NotNull
    @NotBlank(message = "Firstname should be not empty")
    private String firstname;

    @Size(max = 32, message = "Length of lastname should be shorter than 32 characters")
    @NotNull
    @NotBlank(message = "Lastname should be not empty")
    private String lastname;

    @Size(max = 32, message = "Length of patronymic should be shorter than 32 characters")
    @NotBlank(message = "Patronymic should be not empty")
    private String patronymic;

    @Size(max = 255, message = "Length of email should be shorter than 255 characters")
    @NotNull
    @NotBlank(message = "Email should be not empty")
    private String email;

    @Size(max = 128, message = "Length of password should be shorter than 128 characters")
    @NotNull
    @NotBlank(message = "Password should be not empty")
    private String password;
}
