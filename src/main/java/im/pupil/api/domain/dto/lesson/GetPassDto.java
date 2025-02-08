package im.pupil.api.domain.dto.lesson;

import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.time.Instant;

public class GetPassDto implements Serializable {

    @Nullable
    private Integer id;

    private Instant date;
    private Integer status;

    public GetPassDto () {}

    public GetPassDto(@Nullable Integer id, Instant date, Integer status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}



























