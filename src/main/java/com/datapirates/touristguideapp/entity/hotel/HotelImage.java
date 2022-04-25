package com.datapirates.touristguideapp.entity.hotel;

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
@IdClass(HotelImageId.class)
public class HotelImage {
    @Id
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "hotel_id", foreignKey = @ForeignKey(name = "hotel_image_fk1"))
    @JsonBackReference(value = "hotel-hotelImages")
    @ToString.Exclude
    private Hotel hotel;
}
