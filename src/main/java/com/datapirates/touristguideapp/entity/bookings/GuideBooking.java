package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.users.Guide;
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
@ToString
public class GuideBooking extends Booking {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "guide_id", foreignKey = @ForeignKey(name = "guide_booking_fk1"))
    @JsonBackReference(value = "guide-guideBookings")
    @ToString.Exclude
    private Guide guide;
}
