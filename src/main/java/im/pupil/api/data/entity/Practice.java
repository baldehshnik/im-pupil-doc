package im.pupil.api.data.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import im.pupil.api.data.entity.institution.EducationalInstitution;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "practice", schema = "im_pupil", indexes = {
        @Index(name = "institution_id", columnList = "institution_id")
})
public class Practice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "icon")
    private String icon;

    @NotNull
    @Column(name = "pay_ability", nullable = false)
    private Boolean payAbility = false;

    @NotNull
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "work_type", nullable = false)
    private Integer workType;

    @Size(max = 32)
    @NotNull
    @Column(name = "title", nullable = false, length = 32)
    private String title;

    @Lob
    @Column(name = "website")
    private String website;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "institution_id", nullable = false)
    private EducationalInstitution institution;

    @OneToMany(mappedBy = "practice")
    @JsonManagedReference
    private Set<InformationBlock> informationBlocks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "practice")
    @JsonManagedReference
    private Set<Relocation> relocations = new LinkedHashSet<>();

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

    public EducationalInstitution getInstitution() {
        return institution;
    }

    public void setInstitution(EducationalInstitution institution) {
        this.institution = institution;
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