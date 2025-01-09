package im.pupil.api.dto.about;

import jakarta.annotation.Nullable;

import java.io.Serializable;

public class UpdateAboutDto implements Serializable {

    @Nullable
    private Integer id;

    @Nullable
    private String description;

    public UpdateAboutDto(
            @Nullable Integer id,
            @Nullable String description
    ) {
        this.id = id;
        this.description = description;
    }

    public UpdateAboutDto() {
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}



























