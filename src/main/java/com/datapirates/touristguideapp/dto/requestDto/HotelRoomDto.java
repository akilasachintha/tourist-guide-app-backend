package com.datapirates.touristguideapp.dto.requestDto;

import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import lombok.Data;

@Data
public class HotelRoomDto {

    private HotelRoom hotelRoom;

    private String categoryType;

    private Long hoteId;
}
