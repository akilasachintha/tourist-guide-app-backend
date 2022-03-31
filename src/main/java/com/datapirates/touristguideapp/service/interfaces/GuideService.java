package com.datapirates.touristguideapp.service.interfaces;

import com.datapirates.touristguideapp.model.user.Guide;

import java.util.List;

public interface GuideService {
    Guide saveGuide(Guide guide);

    List<Guide> getGuides();

    Guide updateDriver(Guide guide);

    String deleteGuide(Long id);
}
