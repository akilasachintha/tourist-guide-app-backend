package com.datapirates.touristguideapp.model.booking;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Booking")
@Table(name = "booking")
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "check_in_date")
    private String checkInDate;

    @Column(name = "check_out_date")
    private String checkOutDate;

    @Column(name = "paid_amount")
    private double paidAmount;

    @Column(name = "date_booked")
    private String dateBooked;

    @Column(name = "time_booked")
    private String timeBooked;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Booking booking = (Booking) o;
        return bookingId != null && Objects.equals(bookingId, booking.bookingId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
