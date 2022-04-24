package com.datapirates.touristguideapp.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LocationImageId implements Serializable {
    private Location location;

    private String url;
}
