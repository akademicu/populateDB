package com.akademicu.databasepopulate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Getter
@Setter
public class Style {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String styleName;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "style_tattoo_mapping", joinColumns = @JoinColumn(name = "style_id"),
            inverseJoinColumns = @JoinColumn(name = "tattoo_id"))
    @JsonIgnore
    private List<Tattoo> tattoos;
}
