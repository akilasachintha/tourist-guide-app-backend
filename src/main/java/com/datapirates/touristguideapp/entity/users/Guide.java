package com.datapirates.touristguideapp.entity.users;

import com.datapirates.touristguideapp.entity.GuideLanguage;
import com.datapirates.touristguideapp.entity.bookings.GuideBooking;
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
public class Guide extends User {

    private double rating;

    private double priceRange;

    private String NIC;

    private String availability;

    @OneToMany(mappedBy = "guide",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "guide-guideLanguages")
    @ToString.Exclude
    private Set<GuideLanguage> guideLanguages = new HashSet<>();

    @OneToMany(mappedBy = "guide",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "guide-guideBookings")
    @ToString.Exclude
    private Set<GuideBooking> guideBookings = new HashSet<>();
}
