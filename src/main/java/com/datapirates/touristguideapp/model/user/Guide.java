package com.datapirates.touristguideapp.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity(name = "Guide")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Guide extends User {
    @Column(name = "rating")
    private double rating;

    @Column(name = "nic")
    private String nic;

    @Column(name = "availability")
    private String availability;

    @Column(name = "price_range")
    private double priceRange;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Guide guide = (Guide) o;
        return getUserId() != null && Objects.equals(getUserId(), guide.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
