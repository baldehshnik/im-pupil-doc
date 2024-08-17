package im.pupil.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "section", schema = "im_pupil", indexes = {
        @Index(name = "institution_id", columnList = "institution_id")
})
public class Section {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 32)
    @NotNull
    @Column(name = "title", nullable = false, length = 32)
    private String title;

    @Size(max = 128)
    @NotNull
    @Column(name = "trainer", nullable = false, length = 128)
    private String trainer;

    @NotNull
    @Column(name = "price", nullable = false)
    private Boolean price = false;

    @NotNull
    @Column(name = "gender", nullable = false)
    private Integer gender;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "icon")
    private String icon;

    @Column(name = "from_course")
    private Integer fromCourse;

    @Column(name = "to_course")
    private Integer toCourse;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "institution_id", nullable = false)
    private EducationalInstitution institution;

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

    public EducationalInstitution getInstitution() {
        return institution;
    }

    public void setInstitution(EducationalInstitution institution) {
        this.institution = institution;
    }

}