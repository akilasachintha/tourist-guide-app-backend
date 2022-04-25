package com.datapirates.touristguideapp.entity;

import com.datapirates.touristguideapp.entity.users.Guide;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@IdClass(GuideLanguageId.class)
public class GuideLanguage {

    @Id
    private String language;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "guide_id", foreignKey = @ForeignKey(name = "guide_language_fk1"))
    @JsonBackReference(value = "guide-guideLanguages")
    @ToString.Exclude
    private Guide guide;
}
