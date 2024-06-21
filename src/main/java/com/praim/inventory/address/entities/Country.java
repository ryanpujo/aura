package com.praim.inventory.address.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity(name = "countries")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<StateProvince> stateProvinces;
}
