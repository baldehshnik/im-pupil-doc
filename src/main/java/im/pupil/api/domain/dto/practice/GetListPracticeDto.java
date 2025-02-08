package im.pupil.api.domain.dto.practice;

import im.pupil.api.domain.annotation.GetterMethod;
import im.pupil.api.domain.annotation.SetterMethod;

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

    @GetterMethod
    public Integer getId() {
        return id;
    }

    @SetterMethod
    public void setId(Integer id) {
        this.id = id;
    }

    @GetterMethod
    public String getIcon() {
        return icon;
    }

    @SetterMethod
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @GetterMethod
    public Boolean getPayAbility() {
        return payAbility;
    }

    @SetterMethod
    public void setPayAbility(Boolean payAbility) {
        this.payAbility = payAbility;
    }

    @GetterMethod
    public String getDescription() {
        return description;
    }

    @SetterMethod
    public void setDescription(String description) {
        this.description = description;
    }

    @GetterMethod
    public String getTitle() {
        return title;
    }

    @SetterMethod
    public void setTitle(String title) {
        this.title = title;
    }
}
























