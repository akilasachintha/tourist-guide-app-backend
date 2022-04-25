package com.datapirates.touristguideapp.entity.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@IdClass(HotelRoomId.class)
@ToString
public class HotelRoom {

    @Id
    private Long roomNo;

    private String roomCondition;

    private String roomAvailability;

    private double price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "hotel_id", foreignKey = @ForeignKey(name = "hotel_room_fk1"))
    @JsonBackReference(value = "hotel-hotelRooms")
    @ToString.Exclude
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "category_type", foreignKey = @ForeignKey(name = "hotel_room_fk2"))
    @JsonBackReference(value = "roomCategory-hotelRooms")
    @ToString.Exclude
    private RoomCategory roomCategory;
}
