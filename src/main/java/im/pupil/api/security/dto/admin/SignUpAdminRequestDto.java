package im.pupil.api.security.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SignUpAdminRequestDto {
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

    @NotNull
    @NotBlank
    @Size(max = 128, message = "Length of education institution abbreviation should be shorter than 128 characters")
    private String educationalInstitutionAbbreviation;

    public @Size(max = 32) @NotNull @NotBlank(message = "Firstname should be not empty") String getFirstname() {
        return firstname;
    }

    public void setFirstname(@Size(max = 32) @NotNull @NotBlank(message = "Firstname should be not empty") String firstname) {
        this.firstname = firstname;
    }

    public @Size(max = 32) @NotNull @NotBlank(message = "Lastname should be not empty") String getLastname() {
        return lastname;
    }

    public void setLastname(@Size(max = 32) @NotNull @NotBlank(message = "Lastname should be not empty") String lastname) {
        this.lastname = lastname;
    }

    public @Size(max = 32) @NotBlank(message = "Patronymic should be not empty") String getPatronymic() {
        return patronymic;
    }

    public @Size(max = 256) @NotNull @NotBlank(message = "Email should be not empty") String getEmail() {
        return email;
    }

    public void setPatronymic(@Size(max = 32) @NotBlank(message = "Patronymic should be not empty") String patronymic) {
        this.patronymic = patronymic;
    }

    public @NotNull @NotBlank @Size(max = 128, message = "Length of education institution abbreviation should be shorter than 128 characters") String getEducationalInstitutionAbbreviation() {
        return educationalInstitutionAbbreviation;
    }

    public void setEducationalInstitutionAbbreviation(@NotNull @NotBlank @Size(max = 128, message = "Length of education institution abbreviation should be shorter than 128 characters") String educationalInstitutionAbbreviation) {
        this.educationalInstitutionAbbreviation = educationalInstitutionAbbreviation;
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
