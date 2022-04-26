package com.datapirates.touristguideapp.dto.responseDto;

import lombok.Data;

import javax.persistence.Lob;
import java.util.List;

@Data
public class LocationLocationImageDTO {

    private Long locationId;

    private String locationName;

    private String district;

    private String town;

    @Lob
    private String description;

    private String category;

    private List<String> urls;
}
