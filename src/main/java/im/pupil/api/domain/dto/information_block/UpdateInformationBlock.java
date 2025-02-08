package im.pupil.api.domain.dto.information_block;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UpdateInformationBlock implements Serializable {

    @Nullable
    private Integer id = null;

    @NotNull
    @Size(max = 32)
    @NotEmpty(message = "Information block should be not empty")
    @NotBlank(message = "Information block should be not blank")
    private String title;

    @NotNull
    @NotEmpty(message = "Content should be not empty")
    @NotBlank
    private String content;

    public UpdateInformationBlock(@Nullable Integer id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public UpdateInformationBlock() {
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public @NotNull @Size(max = 32) @NotEmpty(message = "Information block should be not empty") @NotBlank(message = "Information block should be not blank") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull @Size(max = 32) @NotEmpty(message = "Information block should be not empty") @NotBlank(message = "Information block should be not blank") String title) {
        this.title = title;
    }

    public @NotNull @NotEmpty(message = "Content should be not empty") @NotBlank String getContent() {
        return content;
    }

    public void setContent(@NotNull @NotEmpty(message = "Content should be not empty") @NotBlank String content) {
        this.content = content;
    }
}
































