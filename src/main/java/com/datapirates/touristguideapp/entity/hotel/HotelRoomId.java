package com.datapirates.touristguideapp.entity.hotel;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotelRoomId implements Serializable {
    private Long roomNo;
    private Hotel hotel;

}
