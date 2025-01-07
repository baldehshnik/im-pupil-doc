package im.pupil.api.dto.relocation;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UpdateRelocationDto implements Serializable {

    @Nullable
    private Integer id = null;

    @NotNull(message = "Name should be not null")
    @Size(max = 32)
    @NotEmpty(message = "Name should be not empty")
    @NotBlank(message = "Name should be not blank")
    private String name;

    public UpdateRelocationDto(@Nullable Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public UpdateRelocationDto() {}

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public @NotNull(message = "Name should be not null") @Size(max = 32) @NotEmpty(message = "Name should be not empty") @NotBlank(message = "Name should be not blank") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Name should be not null") @Size(max = 32) @NotEmpty(message = "Name should be not empty") @NotBlank(message = "Name should be not blank") String name) {
        this.name = name;
    }
}

























