package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.users.Tourist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long relativeTemporaryId;

    private String bookingStatus;

    private String checkInDate;

    private String checkOutDate;

    private double paidAmount;

    private String date;

    private String time;

    private int noOfMembers;

    private String participant;

    private double advancedPayment;

    private Long driverId;

    private Long guideId;

    private Long hotelId;

    @OneToMany(mappedBy = "bookingid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("booking-temporaryBookings")
    @ToString.Exclude
    private Set<TemporaryBooking> temporaryBookings;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "tourist", foreignKey = @ForeignKey(name = "tourist_booking_fk1"))
    @JsonBackReference(value = "booking-tourist")
    @ToString.Exclude
    private Tourist tourist;


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "bookingId = " + bookingId + ", " +
                "relativeTemporaryId = " + relativeTemporaryId + ", " +
                "bookingStatus = " + bookingStatus + ", " +
                "checkInDate = " + checkInDate + ", " +
                "checkOutDate = " + checkOutDate + ", " +
                "paidAmount = " + paidAmount + ", " +
                "date = " + date + ", " +
                "time = " + time + ")";
    }
}
