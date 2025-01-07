package im.pupil.api.dto.practice;

import im.pupil.api.model.InformationBlock;
import im.pupil.api.model.Relocation;

import java.io.Serializable;
import java.util.Set;

public class GetPracticeDto implements Serializable {

    private Integer id;
    private String icon;
    private Boolean payAbility;
    private String description;
    private Integer workType;
    private String title;
    private String website;
    private Set<InformationBlock> informationBlocks;
    private Set<Relocation> relocations;

    public GetPracticeDto() {
    }

    public GetPracticeDto(
            Integer id,
            String icon,
            Boolean payAbility,
            String description,
            Integer workType,
            String title,
            String website,
            Set<InformationBlock> informationBlocks,
            Set<Relocation> relocations
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

    public Boolean getPayAbility() {
        return payAbility;
    }

    public void setPayAbility(Boolean payAbility) {
        this.payAbility = payAbility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<InformationBlock> getInformationBlocks() {
        return informationBlocks;
    }

    public void setInformationBlocks(Set<InformationBlock> informationBlocks) {
        this.informationBlocks = informationBlocks;
    }

    public Set<Relocation> getRelocations() {
        return relocations;
    }

    public void setRelocations(Set<Relocation> relocations) {
        this.relocations = relocations;
    }
}































