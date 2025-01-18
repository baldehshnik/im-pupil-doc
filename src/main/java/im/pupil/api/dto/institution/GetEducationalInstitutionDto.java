package im.pupil.api.dto.institution;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class GetEducationalInstitutionDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String abbreviation;

    @NotNull
    private Integer type;

    private String address;
    private String phone;

    public GetEducationalInstitutionDto(Integer id, String name, String abbreviation, Integer type, String address, String phone) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.type = type;
        this.address = address;
        this.phone = phone;
    }

    public GetEducationalInstitutionDto() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public Integer getType() {
        return type;
    }

    public @NotNull Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setAbbreviation(@NotNull String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setType(@NotNull Integer type) {
        this.type = type;
    }
}
















