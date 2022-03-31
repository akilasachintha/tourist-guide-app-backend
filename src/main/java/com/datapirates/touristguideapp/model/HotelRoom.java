package com.datapirates.touristguideapp.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "HotelRoom")
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "hotel_room")
@ToString
@IdClass(HotelRoomId.class)
public class HotelRoom{

    @Id
    private Long No;

    @Column(name = "availability")
    private String availability;

    @Column(name = "room_condition")
    private String roomCondition;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "hotel_id", foreignKey = @ForeignKey(name = "hotel_room_fk1"))
    private Hotel hotel;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HotelRoom hotelRoom = (HotelRoom) o;
        return No != null && Objects.equals(No, hotelRoom.No);
    }

    @Override
    public int hashCode() {
        return Objects.hash(No);
    }
}
