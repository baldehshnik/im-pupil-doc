package im.pupil.api.dto.section;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UpdateSectionDto implements Serializable {

    @NotNull
    private Integer id;

    @Size(max = 32)
    @NotNull
    private String title;

    @Size(max = 128)
    @NotNull
    private String trainer;

    @NotNull
    private Boolean price;

    @NotNull
    private Integer gender;

    @Nullable
    private String description;

    @Nullable
    private String icon;

    @Nullable
    private Integer fromCourse;

    @Nullable
    private Integer toCourse;

    public UpdateSectionDto() {
    }

    public UpdateSectionDto(
            Integer id,
            String title,
            String trainer,
            Boolean price,
            Integer gender,
            @Nullable String description,
            @Nullable String icon,
            @Nullable Integer fromCourse,
            @Nullable Integer toCourse
    ) {
        this.id = id;
        this.title = title;
        this.trainer = trainer;
        this.price = price;
        this.gender = gender;
        this.description = description;
        this.icon = icon;
        this.fromCourse = fromCourse;
        this.toCourse = toCourse;
    }

    public @NotNull Integer getId() {
        return id;
    }

    public void setId(@NotNull Integer id) {
        this.id = id;
    }

    public @Size(max = 32) @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@Size(max = 32) @NotNull String title) {
        this.title = title;
    }

    public @Size(max = 128) @NotNull String getTrainer() {
        return trainer;
    }

    public void setTrainer(@Size(max = 128) @NotNull String trainer) {
        this.trainer = trainer;
    }

    public @NotNull Boolean getPrice() {
        return price;
    }

    public void setPrice(@NotNull Boolean price) {
        this.price = price;
    }

    public @NotNull Integer getGender() {
        return gender;
    }

    public void setGender(@NotNull Integer gender) {
        this.gender = gender;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Nullable
    public String getIcon() {
        return icon;
    }

    public void setIcon(@Nullable String icon) {
        this.icon = icon;
    }

    @Nullable
    public Integer getFromCourse() {
        return fromCourse;
    }

    public void setFromCourse(@Nullable Integer fromCourse) {
        this.fromCourse = fromCourse;
    }

    @Nullable
    public Integer getToCourse() {
        return toCourse;
    }

    public void setToCourse(@Nullable Integer toCourse) {
        this.toCourse = toCourse;
    }
}



























