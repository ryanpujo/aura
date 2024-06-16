package com.praim.inventory.address.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "countries")
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<StateProvince> stateProvinces;
}
