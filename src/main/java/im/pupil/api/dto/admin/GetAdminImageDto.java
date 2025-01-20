package im.pupil.api.dto.admin;

import java.io.Serializable;

public class GetAdminImageDto implements Serializable {

    private String icon;

    public GetAdminImageDto() {}

    public GetAdminImageDto(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
