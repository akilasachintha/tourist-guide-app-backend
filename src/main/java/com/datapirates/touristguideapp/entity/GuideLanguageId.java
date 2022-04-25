package com.datapirates.touristguideapp.entity;

import com.datapirates.touristguideapp.entity.users.Guide;
import lombok.Data;

import java.io.Serializable;

@Data
public class GuideLanguageId implements Serializable {
    private String language;
    private Guide guide;
}
