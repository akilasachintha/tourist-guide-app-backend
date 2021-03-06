package com.datapirates.touristguideapp.service.impl;

import com.datapirates.touristguideapp.dto.requestDto.HotelReqDTO;
import com.datapirates.touristguideapp.dto.requestDto.HotelRoomDto;
import com.datapirates.touristguideapp.dto.responseDto.AvailableHotelDTO;
import com.datapirates.touristguideapp.dto.responseDto.HotelResponseDTO;
import com.datapirates.touristguideapp.entity.bookings.Booking;
import com.datapirates.touristguideapp.entity.hotel.Hotel;
import com.datapirates.touristguideapp.entity.hotel.HotelImage;
import com.datapirates.touristguideapp.entity.hotel.HotelRoom;
import com.datapirates.touristguideapp.entity.hotel.RoomCategory;
import com.datapirates.touristguideapp.entity.location.Location;
import com.datapirates.touristguideapp.entity.users.HotelOwner;
import com.datapirates.touristguideapp.repository.*;
import com.datapirates.touristguideapp.repository.exception.ResourceNotFoundException;
import com.datapirates.touristguideapp.service.interfaces.hotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class hotelServiceImpl implements hotelService {

    @Autowired
    private hotelRepository hotelRepository;

    @Autowired
    private roomRepository roomRepository;

    @Autowired
    private roomCategoryRepository roomCategoryRepository;


    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private hotelOwnerRepository hotelOwnerRepository;

    @Autowired
    private bookingRepository bookingRepository;

    @Autowired
    private HotelImageRepository hotelImageRepository;

    @Override
    public List<RoomCategory> getAllCategories() {
        return roomCategoryRepository.findAll();
    }

    @Override
    public RoomCategory saveCategory(RoomCategory roomCategory) {
        return roomCategoryRepository.save(roomCategory);
    }

    @Override
    public String updateCategory(String id, RoomCategory roomCategory) {
        Optional<RoomCategory> checking = roomCategoryRepository.findById(id);
        if (!checking.isPresent()) {
            return "not available Id";
        }
        roomCategoryRepository.save(roomCategory);
        return "update success";
    }

    @Override
    public Hotel saveHotel(HotelReqDTO hotelReqDTO) {
        return convertDtoToEntity(hotelReqDTO);
    }

    @Override
    public HotelRoom saveHotelRoom(HotelRoomDto hotelRoomDto) {
        return convertDatoToEntity(hotelRoomDto);
    }

    @Override
    public List<HotelResponseDTO> getHotelsByAppUserId(Long userId) {
        List<Hotel> existingHotels = hotelRepository.findAllByUserId(userId);

        return existingHotels.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public String deleteHotel(Long id) {
        List<Booking> bookings = bookingRepository.findAll();
        for (Booking booking : bookings){
            if (Objects.equals(booking.getHotelId(), id)){
                return "Cant Delete";
            }
        }
        Optional<Hotel> hotel1 = hotelRepository.findById(id);
        if (hotel1.isPresent()) {
            Hotel hotel = hotel1.get();
            Set<HotelRoom> hotelRooms = hotel.getHotelRooms();
            for (HotelRoom hotelRoom : hotelRooms) {
                roomRepository.deleteRoom(hotelRoom.getRoomId());
            }
            Set<HotelImage> hotelImages = hotel.getHotelImages();
            for (HotelImage hotelImage : hotelImages) {
                hotelImageRepository.deleteHotelImage(hotelImage.getUrl());
            }
            hotelRepository.deleteHotel(id);
            return "successfully deleted";
        }

        return "Error Id";

    }

//    @Override
//    public String deleteHotel(Long hotelId) {
//        Hotel existingHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel", "Id", hotelId));
//        hotelRepository.delete(existingHotel);
//
//        return "Deleted";
//    }

//    @Override
//    public HotelOwner updateHotelOwner(Long userId, HotelOwner hotelOwner) {
//        HotelOwner existingHotelOwner = hotelOwnerRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HotelOwner", "Id", userId));
//
//        existingHotelOwner.setName(hotelOwner.getName());
//        existingHotelOwner.setUserPhotoUrl(hotelOwner.getUserPhotoUrl());
//        existingHotelOwner.setPhoneNo(hotelOwner.getPhoneNo());
//        hotelOwnerRepository.save(existingHotelOwner);
//        return existingHotelOwner;
//    }


    private HotelResponseDTO convertEntityToDTO(Hotel hotel) {
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();

        hotelResponseDTO.setHotelId(hotel.getHotelId());
        hotelResponseDTO.setHotelOwnerId(hotel.getHotelOwner().getUserId());
        hotelResponseDTO.setTown(hotel.getTown());
        hotelResponseDTO.setName(hotel.getName());
        hotelResponseDTO.setAdminStatus(hotel.getAdminStatus());
        hotelResponseDTO.setDistrict(hotel.getDistrict());
        hotelResponseDTO.setDescription(hotel.getDescription());
        hotelResponseDTO.setNo(hotel.getNo());
        hotelResponseDTO.setHotelRooms(hotel.getHotelRooms());
        hotelResponseDTO.setHotelImages(hotel.getHotelImages());

        return hotelResponseDTO;
    }

    private Hotel convertDtoToEntity(HotelReqDTO hotelReqDTO) {
        Hotel hotel = new Hotel();

        hotel.setName(hotelReqDTO.getName());
        hotel.setDescription(hotelReqDTO.getDescription());
        hotel.setDistrict(hotelReqDTO.getDistrict());
        hotel.setTown(hotelReqDTO.getTown());
        hotel.setNo(hotelReqDTO.getNo());

        Location existingLocation = locationRepository.findById(hotelReqDTO.getLocationId()).orElseThrow(() ->
                new ResourceNotFoundException("Location", "Id", hotelReqDTO.getLocationId()));
        hotel.setLocation(existingLocation);

        HotelOwner existingHotelOwner = hotelOwnerRepository.findById(hotelReqDTO.getHotelOwnerId()).orElseThrow(() ->
                new ResourceNotFoundException("Hotel Owner", "Id", hotelReqDTO.getHotelOwnerId()));
        hotel.setHotelOwner(existingHotelOwner);

        hotel.setHotelImages(hotelReqDTO.getHotelImages());

        return hotelRepository.save(hotel);
    }


    private HotelRoom convertDatoToEntity(HotelRoomDto hotelRoomDto) {
        HotelRoom hotelRoom = new HotelRoom();

        hotelRoom.setRoomNo(hotelRoomDto.getRoomNo());
        hotelRoom.setRoomAvailability(hotelRoomDto.getRoomAvailability());
        hotelRoom.setRoomCondition(hotelRoomDto.getRoomCondition());
        hotelRoom.setPrice(hotelRoomDto.getPrice());
        hotelRoom.setRoomAvailability(hotelRoomDto.getRoomAvailability());

        RoomCategory existingCategory = roomCategoryRepository.findById(hotelRoomDto.getCategoryType()).orElseThrow(() ->
                new ResourceNotFoundException("Owner", "Id", hotelRoomDto.getCategoryType()));
        hotelRoom.setRoomCategory(existingCategory);

        Hotel existingHotel = hotelRepository.findById(hotelRoomDto.getHotelId()).orElseThrow(() -> new ResourceNotFoundException("Hotel", "Id", hotelRoomDto.getHotelId()));
        hotelRoom.setHotel(existingHotel);

        return roomRepository.save(hotelRoom);
    }

    @Override
    public String deleteCategory(String type) {
        Optional<RoomCategory> roomCategory = roomCategoryRepository.findById(type);
        if (roomCategory.isPresent()) {
            List<HotelRoom> hotelRooms = roomCategory.get().getHotelRooms();
            for (HotelRoom hotelRoom : hotelRooms) {
                roomRepository.deleteRoom(hotelRoom.getRoomId());
            }
            roomCategoryRepository.deleteCategory(type);
            return "successfully deleted";
        }
        return "Error Id";
    }

    @Override
    public List<AvailableHotelDTO> getAvailableHotels(String type, int amount, String startCount) {
        List<Hotel> hotels = hotelRepository.findAll();
        List<AvailableHotelDTO> availableHotelDTOS = new ArrayList<>();
        List<Booking> bookings = bookingRepository.findAll();
        List<Hotel> hotels1 = new ArrayList<>();
        double price = 0.0;
        for (Hotel hotel : hotels) {
            Set<HotelRoom> hotelRooms = hotel.getHotelRooms();
            if (hotelRooms.isEmpty()) {
                //hotels.remove(hotel);
                continue;
            }
            int count = 0;
            for (Booking booking : bookings) {
                if (booking.getCategoryType() != null) {
                    if (booking.getCategoryType().equals(type) && booking.getCheckOutDate() != null && booking.getHotelId().equals(hotel.getHotelId())) {
                        long bookingEndTime = Long.parseLong(booking.getCheckOutDate());
                        long bookingStartTime = Long.parseLong(startCount);
                        if (bookingEndTime < bookingStartTime) {
                            count += booking.getRoomCount();
                        }
                    }
                }
            }
            for (HotelRoom hotelRoom : hotelRooms) {
                RoomCategory roomCategory = hotelRoom.getRoomCategory();
                if (roomCategory.getCategoryType().equals(type)&&hotelRoom.getRoomAvailability().equals("yes")&&hotelRoom.getAdminStatus().equalsIgnoreCase("confirm")) {
                    count++;

                }
            }

            if (count >= amount) {
                //hotels.remove(hotel);
                if (hotel.getAdminStatus().equalsIgnoreCase("confirm")){
                    AvailableHotelDTO availableHotelDTO = new AvailableHotelDTO();
                    availableHotelDTO.setHotel(hotel);
                    availableHotelDTO.setPrice(price);
                    availableHotelDTOS.add(availableHotelDTO);
                }
            }
        }
        return availableHotelDTOS;
    }


    @Override
    public List<HotelRoom> getHotelRoom() {
        return roomRepository.findAll();
    }

    @Override
    public String updateAvailability(Long id, String availability) {
        Optional<HotelRoom> checking = roomRepository.findById(id);
        if (!checking.isPresent()) {
            return "not available Id";
        }
        roomRepository.setAvailability(id, availability);
        return "update success";
    }

    @Override
    public void updateCategoryType(String category, String type) {
        roomRepository.setCategory(category, type);
    }

    @Override
    public String hotelRating(Long id, int starCount) {
        Optional<Hotel> checking = hotelRepository.findById(id);
        if (!checking.isPresent()) {
            return "not available Id";
        }
        double currentRate = hotelRepository.getRate(id);
        Long currentAmount = hotelRepository.getRateAmount(id);
        double currentStars = currentAmount * currentRate;

        currentStars += starCount;
        currentAmount += 1;
        currentRate = currentStars / currentAmount;
        hotelRepository.setRate(id, currentRate);
        hotelRepository.setRateAmount(id, currentAmount);
        return "successful rated";
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Optional<Hotel> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public String updateHotel(Long id, Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel", "Id", id));

        existingHotel.setHotelImages(hotel.getHotelImages());
        existingHotel.setName(hotel.getName());
        existingHotel.setDescription(hotel.getDescription());

        hotelRepository.save(existingHotel);
        return "update success";
    }

    @Override
    public Long getOwnerId(Long hotelId) {
        return hotelRepository.getOwnerId(hotelId);
    }

    @Override
    public void updateOwner(Long owner, Long id) {
        hotelRepository.setOwner(id, owner);
    }

    /*** booking rooms **/
}
