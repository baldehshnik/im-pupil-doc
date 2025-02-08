package im.pupil.api.domain.dto.schedule;

import java.io.Serializable;
import java.time.Instant;

public class GetScheduleDto implements Serializable {

    private Integer id;
    private String name;
    private Instant finishDate;
    private Integer startType;
    private Instant startDate;
    private Integer type;

    public GetScheduleDto() {}

    public GetScheduleDto(
            Integer id,
            String name,
            Instant finishDate,
            Integer startType,
            Instant startDate,
            Integer type
    ) {
        this.id = id;
        this.name = name;
        this.finishDate = finishDate;
        this.startType = startType;
        this.startDate = startDate;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Instant finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getStartType() {
        return startType;
    }

    public void setStartType(Integer startType) {
        this.startType = startType;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}





























