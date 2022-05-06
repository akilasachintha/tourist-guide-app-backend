package com.datapirates.touristguideapp.entity.bookings;

import com.datapirates.touristguideapp.entity.users.Guide;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "guide_booking_fk1"))
public class GuideBooking extends Booking {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")

    private Guide guide;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GuideBooking that = (GuideBooking) o;
        return getBookingId() != null && Objects.equals(getBookingId(), that.getBookingId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
