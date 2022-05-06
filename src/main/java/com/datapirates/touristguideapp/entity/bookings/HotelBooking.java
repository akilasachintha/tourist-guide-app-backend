package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.javadoc.internal.doclets.toolkit.taglets.UserTaglet;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import org.hibernate.annotations.ManyToAny;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "hotel_booking_fk1"))
public class HotelBooking extends Booking {

    private int noOfMembers;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "hotel_id", referencedColumnName = "hotelId")
    private Hotel hotel;

    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "hotelBookings")
    private Set<HotelRoom> hotelRooms = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HotelBooking that = (HotelBooking) o;
        return getBookingId() != null && Objects.equals(getBookingId(), that.getBookingId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
