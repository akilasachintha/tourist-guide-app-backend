package com.datapirates.touristguideapp.entity.hotel;

import com.datapirates.touristguideapp.entity.bookings.HotelBooking;
import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hotelId;

    private double rating;

    private Long rateAmount;

    private String name;

    private String No;

    private String district;

    private String town;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel-hotelImages")
    @ToString.Exclude
    private Set<HotelImage> hotelImages = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel-hotelRooms")
    @ToString.Exclude
    private Set<HotelRoom> hotelRooms = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "hotel_fk1"))
    @JsonBackReference(value = "location-hotels")
    @ToString.Exclude
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "hotel_owner_id", referencedColumnName = "userId")
    private HotelOwner hotelOwner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hotel hotel = (Hotel) o;
        return hotelId != null && Objects.equals(hotelId, hotel.hotelId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


