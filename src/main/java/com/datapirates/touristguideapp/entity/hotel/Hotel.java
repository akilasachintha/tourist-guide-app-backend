package com.datapirates.touristguideapp.entity.hotel;

import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.bookings.HotelBooking;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    private double rating;

    private String name;

    private String No;

    private String district;

    private String town;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel-hotelImages")
    private Set<HotelImage> hotelImages = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel-hotelRooms")
    private Set<HotelRoom> hotelRooms = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel-hotelBookings")
    private Set<HotelBooking> hotelBookings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "hotel_fk1"))
    @JsonBackReference(value = "location-hotels")
    @ToString.Exclude
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "hotel_owner_id", foreignKey = @ForeignKey(name = "hotel_fk2"))
    @JsonBackReference(value = "hotelOwner-hotels")
    @ToString.Exclude
    private HotelOwner hotelOwner;
}


