package com.praim.inventory.address.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity(name = "cities")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "state_province_id"}))
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "state_province_id")
    @EqualsAndHashCode.Exclude
    private StateProvince stateProvince;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Address> addresses;
}
