package com.datapirates.touristguideapp.entity.hotel;

import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.location.LocationImage;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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

    private String adminStatus="pending";

    private String description;

    private String specialOffers;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel-hotelImages")
    @ToString.Exclude
    private Set<HotelImage> hotelImages = new HashSet<>();

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotel-hotelRooms")
    @ToString.Exclude
    private Set<HotelRoom> hotelRooms =  new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "location_id", foreignKey = @ForeignKey(name = "hotel_fk1"))
    @JsonBackReference(value = "location-hotels")
    @ToString.Exclude
    private Location location;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "hotelOwner", foreignKey = @ForeignKey(name = "hotel_fk2"))
    @JsonBackReference(value = "hotelOwner-hotels")
    @ToString.Exclude
    private HotelOwner hotelOwner;

    public void setHotelImages(Set<HotelImage> hotelImages) {
        this.hotelImages = hotelImages;

        for (HotelImage b : hotelImages) {
            b.setHotel(this);
        }
    }


    public HotelOwner getHotelOwner() {
        return hotelOwner;
    }

    public void setHotelOwner(HotelOwner hotelOwner) {
        this.hotelOwner = hotelOwner;
    }
}


