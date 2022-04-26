package com.datapirates.touristguideapp.entity.hotel;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotelImageId implements Serializable {
    private Hotel hotel;
    private String url;
}
