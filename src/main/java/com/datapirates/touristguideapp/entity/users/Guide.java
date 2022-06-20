package com.datapirates.touristguideapp.entity.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "guide_fk1"))
public class Guide extends AppUser {

    private String userType = "guide";

    private double priceRange;

    private String NIC;

    private String availability = "available";

    private String adminStatus = "pending";


}
