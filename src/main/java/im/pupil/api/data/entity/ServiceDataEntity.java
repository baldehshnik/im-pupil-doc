package im.pupil.api.data.entity;

import im.pupil.api.domain.annotation.GetterMethod;
import im.pupil.api.domain.annotation.SetterMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        name = "services",
        schema = "im_pupil"
)
public class ServiceDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @NotNull
    @Column(name = "image_path", unique = true, nullable = false)
    private String imagePath;

    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @NotNull
    @Column(name = "destination", nullable = false)
    private Integer destination;

    @GetterMethod
    public Long getId() {
        return id;
    }

    @SetterMethod
    public void setId(Long id) {
        this.id = id;
    }

    @GetterMethod
    public String getTitle() {
        return title;
    }

    @SetterMethod
    public void setTitle(String title) {
        this.title = title;
    }

    @GetterMethod
    public String getImagePath() {
        return imagePath;
    }

    @SetterMethod
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @GetterMethod
    public Integer getPosition() {
        return position;
    }

    @SetterMethod
    public void setPosition(Integer position) {
        this.position = position;
    }

    @GetterMethod
    public Boolean getEnabled() {
        return enabled;
    }

    @SetterMethod
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @GetterMethod
    public Integer getDestination() {
        return destination;
    }

    @SetterMethod
    public void setDestination(Integer destination) {
        this.destination = destination;
    }
}

















