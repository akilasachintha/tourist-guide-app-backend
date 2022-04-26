package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "hotel_booking_fk1"))
public class HotelBooking extends Booking {

    private int noOfMembers;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "hotel_id", foreignKey = @ForeignKey(name = "hotel_booking_fk2"))
    @JsonBackReference(value = "hotel-hotelBookings")
    @ToString.Exclude
    private Hotel hotel;

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
