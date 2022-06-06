package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.dto.responseDto.LocationLocationImageDTO;
import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.location.LocationImage;
import com.datapirates.touristguideapp.repository.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.repository.LocationRepository;
import com.datapirates.touristguideapp.service.interfaces.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        Location existingLocation = locationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Location", "Id", id));

        existingLocation.setLocationName(location.getLocationName());
        existingLocation.setDistrict(location.getDistrict());
        existingLocation.setTown(location.getTown());
        existingLocation.setCategory(location.getCategory());
        existingLocation.setDescription(location.getDescription());
        existingLocation.setLocationImages(location.getLocationImages());
        existingLocation.setDrivers(location.getDrivers());
        existingLocation.setHotels(location.getHotels());

        locationRepository.save(existingLocation);
        return existingLocation;
    }

    @Override
    public String deleteLocation(Long id) {
        Optional<Location> optionalLibrary = locationRepository.findById(id);
        if (!optionalLibrary.isPresent()) {
            return "Deletion Error";
        }
        locationRepository.delete(optionalLibrary.get());
        return "Location Id " + id + " Deleted Successfully";
    }

    @Override
    public LocationLocationImageDTO getLocationById(Long id) {
        //Normal Approach
//        Optional<Location> location = locationRepository.findById(id);
//
//        if(location.isPresent()){
//            LocationLocationImageDTO dto = location.;
//            return location.get();
//        }
//        else {
//            throw new ResourceNotFoundException("Location", "Id", id);
//        }

        //Lambda Expression Approach
        Location existingLocation = locationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Location", "Id", id));
        return convertEntityToDto(existingLocation);
    }

    @Override
    public List<LocationLocationImageDTO> getAllLocations() {
        return locationRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private LocationLocationImageDTO convertEntityToDto(Location location){
        LocationLocationImageDTO locationLocationImageDTO = new LocationLocationImageDTO();

        locationLocationImageDTO.setLocationId(location.getLocationId());
        locationLocationImageDTO.setLocationName(location.getLocationName());
        locationLocationImageDTO.setDistrict(location.getDistrict());
        locationLocationImageDTO.setTown(location.getTown());
        locationLocationImageDTO.setCategory(location.getCategory());
        locationLocationImageDTO.setDescription(location.getDescription());

        Set<LocationImage> locationImages= location.getLocationImages();
        List<String> list = locationImages.stream().map(LocationImage::getUrl).collect(Collectors.toList());

        locationLocationImageDTO.setUrls(list);

        return locationLocationImageDTO;
    }
}
