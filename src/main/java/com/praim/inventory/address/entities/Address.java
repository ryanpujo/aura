package com.praim.inventory.address.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String addressLine;

    @Column(nullable = false)
    private long postalCode;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private City city;
}
