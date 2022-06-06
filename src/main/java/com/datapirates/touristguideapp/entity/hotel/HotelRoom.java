package com.datapirates.touristguideapp.entity.hotel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;

    private Long roomNo;

    private String roomCondition;

    private String roomAvailability;

    private double price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "hotel", foreignKey = @ForeignKey(name = "hotel_room_fk1"))
    @JsonBackReference(value = "hotel-hotelRooms")
    @ToString.Exclude
    private Hotel hotel;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "roomCategory", foreignKey = @ForeignKey(name = "hotel_room_fk2"))
    @JsonBackReference(value = "hotelcategory-hotelRooms")
    private RoomCategory roomCategory;

   /* @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booking_rooms",joinColumns = {@JoinColumn(name = "booking_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_no")})
    private Set<HotelBooking> hotelBookings = new HashSet<>();*/

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        HotelRoom hotelRoom = (HotelRoom) o;
//        return roomNo != null && Objects.equals(roomNo, hotelRoom.roomNo);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(roomNo);
//    }
}
