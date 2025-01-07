package im.pupil.api.dto.practice;

import im.pupil.api.dto.information_block.UpdateInformationBlock;
import im.pupil.api.dto.relocation.UpdateRelocationDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class UpdatePracticeDto implements Serializable {

    private Integer id;

    private String icon;

    @NotNull
    private Boolean payAbility = false;

    @NotNull
    @NotEmpty(message = "Description should be not empty")
    @NotBlank(message = "Description should be not empty")
    private String description;

    @NotNull
    private Integer workType;

    @NotNull
    @Size(max = 32)
    @NotEmpty(message = "Title should be not empty")
    @NotBlank(message = "Title should be not empty")
    private String title;

    private String website;

    @NotNull(message = "Information block should be not null")
    private Set<UpdateInformationBlock> informationBlocks = new LinkedHashSet<>();

    @NotNull(message = "Relocation should be not null")
    private Set<UpdateRelocationDto> relocations = new LinkedHashSet<>();

    public UpdatePracticeDto(
            Integer id,
            String icon,
            Boolean payAbility,
            String description,
            Integer workType,
            String title,
            String website,
            Set<UpdateInformationBlock> informationBlocks,
            Set<UpdateRelocationDto> relocations
    ) {
        this.id = id;
        this.icon = icon;
        this.payAbility = payAbility;
        this.description = description;
        this.workType = workType;
        this.title = title;
        this.website = website;
        this.informationBlocks = informationBlocks;
        this.relocations = relocations;
    }

    public UpdatePracticeDto() {
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

    public @NotNull Boolean getPayAbility() {
        return payAbility;
    }

    public void setPayAbility(@NotNull Boolean payAbility) {
        this.payAbility = payAbility;
    }

    public @NotNull @NotEmpty(message = "Description should be not empty") @NotBlank(message = "Description should be not empty") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull @NotEmpty(message = "Description should be not empty") @NotBlank(message = "Description should be not empty") String description) {
        this.description = description;
    }

    public @NotNull Integer getWorkType() {
        return workType;
    }

    public void setWorkType(@NotNull Integer workType) {
        this.workType = workType;
    }

    public @NotNull @Size(max = 32) @NotEmpty(message = "Title should be not empty") @NotBlank(message = "Title should be not empty") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull @Size(max = 32) @NotEmpty(message = "Title should be not empty") @NotBlank(message = "Title should be not empty") String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public @NotNull(message = "Information block should be not null") Set<UpdateInformationBlock> getInformationBlocks() {
        return informationBlocks;
    }

    public void setInformationBlocks(@NotNull(message = "Information block should be not null") Set<UpdateInformationBlock> informationBlocks) {
        this.informationBlocks = informationBlocks;
    }

    public @NotNull(message = "Relocation should be not null") Set<UpdateRelocationDto> getRelocations() {
        return relocations;
    }

    public void setRelocations(@NotNull(message = "Relocation should be not null") Set<UpdateRelocationDto> relocations) {
        this.relocations = relocations;
    }
}































