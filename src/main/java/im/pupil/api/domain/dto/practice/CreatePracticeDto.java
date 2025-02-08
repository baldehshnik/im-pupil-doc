package im.pupil.api.domain.dto.practice;

import im.pupil.api.domain.dto.information_block.InformationBlockDto;
import im.pupil.api.domain.dto.relocation.RelocationDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class CreatePracticeDto implements Serializable {

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
    private Set<InformationBlockDto> informationBlocks = new LinkedHashSet<>();

    @NotNull(message = "Relocation should be not null")
    private Set<RelocationDto> relocations = new LinkedHashSet<>();

    public CreatePracticeDto(
            Boolean payAbility,
            String description,
            Integer workType,
            String title,
            String website,
            Set<InformationBlockDto> informationBlocks,
            Set<RelocationDto> relocations
    ) {
        this.payAbility = payAbility;
        this.description = description;
        this.workType = workType;
        this.title = title;
        this.website = website;
        this.informationBlocks = informationBlocks;
        this.relocations = relocations;
    }

    public CreatePracticeDto() {
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

    public @NotNull(message = "Information block should be not null") Set<InformationBlockDto> getInformationBlocks() {
        return informationBlocks;
    }

    public void setInformationBlocks(@NotNull(message = "Information block should be not null") Set<InformationBlockDto> informationBlocks) {
        this.informationBlocks = informationBlocks;
    }

    public @NotNull(message = "Relocation should be not null") Set<RelocationDto> getRelocations() {
        return relocations;
    }

    public void setRelocations(@NotNull(message = "Relocation should be not null") Set<RelocationDto> relocations) {
        this.relocations = relocations;
    }
}

























