package im.pupil.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

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

    public PracticeDto() {
    }

    public PracticeDto(String icon, Boolean payAbility, String description, Integer workType, String title, String website) {
        this.icon = icon;
        this.payAbility = payAbility;
        this.description = description;
        this.workType = workType;
        this.title = title;
        this.website = website;
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
                "payability = " + payAbility + ", " +
                "description = " + description + ", " +
                "workType = " + workType + ", " +
                "title = " + title + ", " +
                "website = " + website + ")";
    }
}