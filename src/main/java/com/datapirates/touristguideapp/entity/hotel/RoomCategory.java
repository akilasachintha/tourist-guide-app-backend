package com.datapirates.touristguideapp.entity.hotel;

import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class RoomCategory {
    @Id
    private String categoryType;

    private String description;

    @OneToMany(mappedBy = "roomCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "roomCategory-hotelRooms")
    private Set<HotelRoom> hotelRooms = new HashSet<>();
}
