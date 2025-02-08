package im.pupil.api.domain.dto.pupil;

import im.pupil.api.domain.annotation.GetterMethod;
import im.pupil.api.domain.annotation.SetterMethod;

import java.io.Serializable;

public class OnlyPupilDto implements Serializable {

    private Integer id;
    private String icon;
    private String code;
    private Integer status;

    public OnlyPupilDto() {
    }

    public OnlyPupilDto(Integer id, String icon, String code, Integer status) {
        this.id = id;
        this.icon = icon;
        this.code = code;
        this.status = status;
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
    public String getCode() {
        return code;
    }

    @SetterMethod
    public void setCode(String code) {
        this.code = code;
    }

    @GetterMethod
    public Integer getStatus() {
        return status;
    }

    @SetterMethod
    public void setStatus(Integer status) {
        this.status = status;
    }
}
























