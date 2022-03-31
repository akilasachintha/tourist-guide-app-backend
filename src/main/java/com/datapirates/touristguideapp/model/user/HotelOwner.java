package com.datapirates.touristguideapp.model.user;

import com.datapirates.touristguideapp.model.Hotel;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "HotelOwner")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class HotelOwner extends User {
    @Column(name = "nic")
    private String nic;

    @OneToMany(mappedBy = "hotelOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Hotel> hotels;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HotelOwner that = (HotelOwner) o;
        return getUserId() != null && Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
