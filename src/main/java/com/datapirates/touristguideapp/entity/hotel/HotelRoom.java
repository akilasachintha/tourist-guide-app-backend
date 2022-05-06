package com.datapirates.touristguideapp.entity.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.datapirates.touristguideapp.entity.bookings.HotelBooking;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@IdClass(HotelRoomId.class)
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "category_type", referencedColumnName = "categoryType")
    private RoomCategory roomCategory;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booking_rooms",joinColumns = {@JoinColumn(name = "booking_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_no")})
    private Set<HotelBooking> hotelBookings = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HotelRoom hotelRoom = (HotelRoom) o;
        return roomNo != null && Objects.equals(roomNo, hotelRoom.roomNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNo);
    }
}
