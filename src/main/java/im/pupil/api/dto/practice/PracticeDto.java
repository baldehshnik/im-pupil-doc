package im.pupil.api.dto.practice;

import im.pupil.api.dto.EducationalInstitutionDto;
import im.pupil.api.dto.information_block.InformationBlockDto;
import im.pupil.api.dto.relocation.RelocationDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO for {@link im.pupil.api.model.Practice}
 */
public class PracticeDto implements Serializable {

    private String icon;

    @NotNull
    private Boolean payAbility = false;

    @NotNull
    @NotEmpty(message = "Description should be not empty")
    @NotBlank(message = "Description should be not empty")
    private String description;

    @NotNull
    private Integer workType;

    @NotNull
    @Size(max = 32)
    @NotEmpty(message = "Title should be not empty")
    @NotBlank(message = "Title should be not empty")
    private String title;

    private String website;

    @NotNull(message = "Institution should be not null")
    private EducationalInstitutionDto institution;

    @NotNull(message = "Information block should be not null")
    private Set<InformationBlockDto> informationBlocks = new LinkedHashSet<>();

    @NotNull(message = "Relocation should be not null")
    private Set<RelocationDto> relocations = new LinkedHashSet<>();

    public PracticeDto() {
    }

    public PracticeDto(
            String icon,
            Boolean payAbility,
            String description,
            Integer workType,
            String title,
            String website,
            EducationalInstitutionDto institution,
            Set<InformationBlockDto> informationBlocks,
            Set<RelocationDto> relocations
    ) {
        this.icon = icon;
        this.payAbility = payAbility;
        this.description = description;
        this.workType = workType;
        this.title = title;
        this.website = website;
        this.institution = institution;
        this.informationBlocks = informationBlocks;
        this.relocations = relocations;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getPayAbility() {
        return payAbility;
    }

    public void setPayAbility(Boolean payAbility) {
        this.payAbility = payAbility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public EducationalInstitutionDto getInstitution() {
        return institution;
    }

    public void setInstitution(EducationalInstitutionDto institution) {
        this.institution = institution;
    }

    public Set<InformationBlockDto> getInformationBlocks() {
        return informationBlocks;
    }

    public void setInformationBlocks(Set<InformationBlockDto> informationBlocks) {
        this.informationBlocks = informationBlocks;
    }

    public Set<RelocationDto> getRelocations() {
        return relocations;
    }

    public void setRelocations(Set<RelocationDto> relocations) {
        this.relocations = relocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PracticeDto entity = (PracticeDto) o;
        return Objects.equals(this.icon, entity.icon) &&
                Objects.equals(this.payAbility, entity.payAbility) &&
                Objects.equals(this.description, entity.description) &&
                Objects.equals(this.workType, entity.workType) &&
                Objects.equals(this.title, entity.title) &&
                Objects.equals(this.website, entity.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icon, payAbility, description, workType, title, website);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "icon = " + icon + ", " +
                "payAbility = " + payAbility + ", " +
                "description = " + description + ", " +
                "workType = " + workType + ", " +
                "title = " + title + ", " +
                "website = " + website + ")";
    }
}