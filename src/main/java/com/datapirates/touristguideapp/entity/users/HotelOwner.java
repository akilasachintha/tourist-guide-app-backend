package com.datapirates.touristguideapp.entity.users;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
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
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "hotel_owner_fk1"))
public class HotelOwner extends AppUser {

    private final String userType = "hotelOwner";

    private String adminStatus = "pending";


    @OneToMany(mappedBy = "hotelOwner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotelOwner-hotels")
    @ToString.Exclude
    private Set<Hotel> hotels = new HashSet<>();
}
