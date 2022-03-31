package com.datapirates.touristguideapp.service.implementation;

import com.datapirates.touristguideapp.model.user.Guide;
import com.datapirates.touristguideapp.repository.GuideRepository;
import com.datapirates.touristguideapp.service.interfaces.GuideService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideServiceImplementation implements GuideService {
    private final GuideRepository guideRepository;

    public GuideServiceImplementation(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    @Override
    public Guide saveGuide(Guide guide) {
        return guideRepository.save(guide);
    }

    @Override
    public List<Guide> getGuides() {
        return guideRepository.findAll();
    }

    @Override
    public Guide updateDriver(Guide guide) {
        Guide existingGuide = guideRepository.findById(guide.getUserId()).orElse(null);
        assert existingGuide != null;
        existingGuide.setName(guide.getName());
        existingGuide.setEmail(guide.getEmail());
        existingGuide.setDob(guide.getDob());
        existingGuide.setPassword(guide.getPassword());
        existingGuide.setAvailability(guide.getAvailability());
        existingGuide.setNic(guide.getNic());
        existingGuide.setPriceRange(guide.getPriceRange());
        existingGuide.setRating(guide.getRating());
        existingGuide.setPhotoUrl(guide.getPhotoUrl());
        return guideRepository.save(existingGuide);
    }

    @Override
    public String deleteGuide(Long id) {
        guideRepository.deleteById(id);
        return "Successfully Deleted Guide " + id;
    }
}
