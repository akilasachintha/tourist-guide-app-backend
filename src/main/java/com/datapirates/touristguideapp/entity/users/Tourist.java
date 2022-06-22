package com.datapirates.touristguideapp.entity.users;

import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.bookings.TemporaryBooking;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "tourist_fk1"))
public class Tourist extends AppUser {

    private String userType = "tourist";

    private String passport;

    private String country;

    private String verifyStatus = "pending";

    private String verifyCode;

   // private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("booking-tourist")
    @ToString.Exclude
    private List<Booking> bookings;
}
