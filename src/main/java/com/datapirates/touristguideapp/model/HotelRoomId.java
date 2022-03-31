package com.datapirates.touristguideapp.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class HotelRoomId implements Serializable {
    private Hotel hotel;
    private Long No;
}
