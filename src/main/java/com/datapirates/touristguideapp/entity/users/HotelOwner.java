package com.datapirates.touristguideapp.entity.users;

import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class HotelOwner extends User {

    @OneToMany(mappedBy = "hotelOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "hotelOwner-hotels")
    private Set<Hotel> hotel = new HashSet<>();
}
