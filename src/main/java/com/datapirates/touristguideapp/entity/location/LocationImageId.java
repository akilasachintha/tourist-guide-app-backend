package com.datapirates.touristguideapp.entity.location;

import lombok.Data;

import java.io.Serializable;

@Data
public class LocationImageId implements Serializable {
    private Location location;

    private String url;
}
