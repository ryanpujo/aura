package com.praim.inventory.address.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "state_provinces")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "country_id"}))
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class StateProvince {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(nullable = false)
    @NonNull
    private  String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "stateProvince", cascade = CascadeType.ALL)
    private List<City> cities;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
