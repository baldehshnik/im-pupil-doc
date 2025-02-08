package im.pupil.api.domain.dto.notification;

import java.io.Serializable;
import java.time.Instant;

public class GetNotificationDto implements Serializable {

    private Integer id;
    private String icon;
    private String title;
    private String description;
    private Instant dateTime;
    private Boolean status;
    private Integer type;

    public GetNotificationDto() {
    }

    public GetNotificationDto(
            Integer id,
            String icon,
            String title,
            String description,
            Instant dateTime,
            Boolean status, Integer type
    ) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
        this.type = type;
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

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}



























