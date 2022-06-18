package com.datapirates.touristguideapp.entity.hotel;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RoomCategory {
    @Id
    private String categoryType;

    private String description;

    @OneToMany(mappedBy = "roomCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "hotelcategory-hotelRooms")
    @ToString.Exclude
    private List<HotelRoom> hotelRooms;
}
