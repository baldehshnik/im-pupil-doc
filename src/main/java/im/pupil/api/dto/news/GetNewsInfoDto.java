package im.pupil.api.dto.news;

import java.io.Serializable;
import java.util.List;

public class GetNewsInfoDto implements Serializable {

    private Integer id;
    private String title;
    private String image;
    private String description;
    private List<GetGuideDto> guides;

    public GetNewsInfoDto() {
    }

    public GetNewsInfoDto(Integer id, String title, String image, String description, List<GetGuideDto> guides) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
        this.guides = guides;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GetGuideDto> getGuides() {
        return guides;
    }

    public void setGuides(List<GetGuideDto> guides) {
        this.guides = guides;
    }
}
























