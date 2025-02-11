package im.pupil.api.domain.dto.services;

import im.pupil.api.domain.annotation.GetterMethod;
import im.pupil.api.domain.annotation.SetterMethod;

import java.io.Serializable;

public class ReadServiceDto implements Serializable {

    private Long id;
    private String title;
    private String imagePath;
    private Integer position;
    private Boolean enabled;
    private Integer destination;

    public ReadServiceDto() {}

    public ReadServiceDto(
            Long id,
            String title,
            String imagePath,
            Integer position,
            Boolean enabled,
            Integer destination
    ) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.position = position;
        this.enabled = enabled;
        this.destination = destination;
    }

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






















