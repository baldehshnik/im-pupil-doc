package im.pupil.api.dto.about;

import java.io.Serializable;

public class GetAboutDto implements Serializable {

    private Integer id;
    private String description;
    private String icon;

    public GetAboutDto() {}

    public GetAboutDto(Integer id, String description, String icon) {
        this.id = id;
        this.description = description;
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
























