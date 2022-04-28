package com.datapirates.touristguideapp.entity.users;

import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;


import javax.persistence.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "tourist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "tourist-bookings")
    @ToString.Exclude
    private Set<Booking> bookings = new HashSet<>();

}
