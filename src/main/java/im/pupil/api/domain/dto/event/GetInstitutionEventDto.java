package im.pupil.api.domain.dto.event;

import java.io.Serializable;

public class GetInstitutionEventDto implements Serializable {

    private Integer id;
    private String title;
    private String description;
    private String image;
    private Integer duration;
    private Integer type;

    public GetInstitutionEventDto() {
    }

    public GetInstitutionEventDto(
            Integer id,
            String title,
            String description,
            String image,
            Integer duration,
            Integer type
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.duration = duration;
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}



























