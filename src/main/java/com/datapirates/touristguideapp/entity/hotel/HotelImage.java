package com.datapirates.touristguideapp.entity.hotel;

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
@IdClass(HotelImageId.class)
public class HotelImage {
    @Id
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "hotel_id", foreignKey = @ForeignKey(name = "hotel_image_fk1"))
    @JsonBackReference(value = "hotel-hotelImages")
    @ToString.Exclude
    private Hotel hotel;
}
