package im.pupil.api.domain.dto.exam;

import java.io.Serializable;
import java.time.Instant;

public class GetExamDto implements Serializable {

    private Integer id;
    private Integer type;
    private String name;
    private String audience;
    private Instant dateTime;
    private Integer status;

    public GetExamDto() {
    }

    public GetExamDto(Integer id, Integer type, String name, String audience, Instant dateTime, Integer status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.audience = audience;
        this.dateTime = dateTime;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
























