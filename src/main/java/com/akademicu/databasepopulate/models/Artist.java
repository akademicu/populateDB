package com.akademicu.databasepopulate.models;

import lombok.*;
import jakarta.persistence.*;


import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table
@Getter
@Setter
public class Artist {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String location;
    private String url;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Tattoo> tattoos;
}
