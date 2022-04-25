package com.datapirates.touristguideapp.entity.users;

import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.users.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Tourist extends User {

    private String passport;

    private String country;

    @OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "tourist-bookings")
    @ToString.Exclude
    private Set<Booking> bookings = new HashSet<>();
}
