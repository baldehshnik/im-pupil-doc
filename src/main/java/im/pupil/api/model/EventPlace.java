package im.pupil.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "event_place", schema = "im_pupil", uniqueConstraints = {
        @UniqueConstraint(name = "event_id", columnNames = {"event_id"})
})
public class EventPlace {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 256)
    @Column(name = "address", length = 256)
    private String address;

    @Size(max = 256)
    @Column(name = "place", length = 256)
    private String place;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", nullable = false)
    private InstitutionEvent event;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public InstitutionEvent getEvent() {
        return event;
    }

    public void setEvent(InstitutionEvent event) {
        this.event = event;
    }

}