package com.datapirates.touristguideapp.entity.hotel;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class RoomCategory {
    @Id
    private String categoryType;

    private String description;


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        RoomCategory that = (RoomCategory) o;
//        return categoryType != null && Objects.equals(categoryType, that.categoryType);
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}
