package com.praim.inventory.address.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String addressLine;

    @Column(nullable = false)
    private long postalCode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id")
    @EqualsAndHashCode.Exclude
    private City city;
}
