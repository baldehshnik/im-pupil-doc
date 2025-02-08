package im.pupil.api.domain.dto.news;

import java.io.Serializable;

public class GetNewsListDto implements Serializable {

    private Integer id;
    private String title;
    private String image;
    private String description;

    public GetNewsListDto() {}

    public GetNewsListDto(Integer id, String title, String image, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.description = description;
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
}






















