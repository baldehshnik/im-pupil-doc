package im.pupil.api.dto.practice;

import java.io.Serializable;

public class GetListPracticeDto implements Serializable {

    private Integer id;
    private String icon;
    private Boolean payAbility;
    private String description;
    private String title;

    public GetListPracticeDto(
            Integer id,
            String icon,
            Boolean payAbility,
            String description,
            String title
    ) {
        this.id = id;
        this.icon = icon;
        this.payAbility = payAbility;
        this.description = description;
        this.title = title;
    }

    public GetListPracticeDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
























