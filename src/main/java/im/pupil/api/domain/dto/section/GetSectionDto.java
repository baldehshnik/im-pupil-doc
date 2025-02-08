package im.pupil.api.domain.dto.section;

import java.io.Serializable;

public class GetSectionDto implements Serializable {

    private Integer id;
    private String title;
    private String trainer;
    private Boolean price;
    private Integer gender;
    private String description;
    private String icon;
    private Integer fromCourse;
    private Integer toCourse;

    public GetSectionDto() {}

    public GetSectionDto(
            Integer id,
            String title,
            String trainer,
            Boolean price,
            Integer gender,
            String description,
            String icon,
            Integer fromCourse,
            Integer toCourse
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public Boolean getPrice() {
        return price;
    }

    public void setPrice(Boolean price) {
        this.price = price;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getFromCourse() {
        return fromCourse;
    }

    public void setFromCourse(Integer fromCourse) {
        this.fromCourse = fromCourse;
    }

    public Integer getToCourse() {
        return toCourse;
    }

    public void setToCourse(Integer toCourse) {
        this.toCourse = toCourse;
    }
}





















