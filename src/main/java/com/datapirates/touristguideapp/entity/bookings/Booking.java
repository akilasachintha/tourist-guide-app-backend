package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.users.Tourist;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private String checkInDate;

    private String checkOutDate;

    private double paidAmount;

    private String date;

    private String time;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("booking-temporaryBookings")
    private Set<TemporaryBooking> temporaryBookings;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "tourist_id", foreignKey = @ForeignKey(name = "booking_fk1"))
    @JsonBackReference("tourist-bookings")
    @ToString.Exclude
    private Tourist tourist;
}
