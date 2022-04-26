package com.datapirates.touristguideapp.entity.bookings;

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
public class TemporaryBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tempBookingId;

    private String driverStatus;

    private String guideStatus;

    private String hotelStatus;

    private String guideEndTime;

    private String hotelEndTime;

    private String vehicleEndTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "booking_id", foreignKey = @ForeignKey(name = "temporary_booking_fk1"))
    @JsonBackReference(value = "booking-temporaryBookings")
    @ToString.Exclude
    private Booking booking;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TemporaryBooking that = (TemporaryBooking) o;
        return tempBookingId != null && Objects.equals(tempBookingId, that.tempBookingId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
