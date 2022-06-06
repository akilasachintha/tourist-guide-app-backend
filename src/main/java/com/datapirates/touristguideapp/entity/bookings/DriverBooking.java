package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.users.Driver;
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
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "driver_booking_fk1"))
public class DriverBooking extends Booking {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "driver",referencedColumnName = "userId")
    private Driver driver;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        DriverBooking that = (DriverBooking) o;
//        return getBookingId() != null && Objects.equals(getBookingId(), that.getBookingId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
