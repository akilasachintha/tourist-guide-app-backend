package com.datapirates.touristguideapp.entity;

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
@IdClass(GuideLanguageId.class)
public class GuideLanguage {

    @Id
    private String language;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "guide_language_fk1"))
    @JsonBackReference(value = "guide-guideLanguages")
    @ToString.Exclude
    private Guide guide;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        GuideLanguage that = (GuideLanguage) o;
//        return language != null && Objects.equals(language, that.language);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(language);
//    }
}
