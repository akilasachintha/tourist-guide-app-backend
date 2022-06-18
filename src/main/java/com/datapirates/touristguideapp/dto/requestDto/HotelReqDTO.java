package com.datapirates.touristguideapp.dto.requestDto;

import com.datapirates.touristguideapp.entity.hotel.HotelImage;
import lombok.Data;
import javax.persistence.Lob;
import java.util.Set;

@Data
public class HotelReqDTO {

    private double rating;

    private Long rateAmount;

    private String name;

    private String No;

    private String district;

    private String town;

    @Lob
    private String description;

    private Long locationId;

    private Set<HotelImage> hotelImages;

    private Long hotelOwnerId;
}

