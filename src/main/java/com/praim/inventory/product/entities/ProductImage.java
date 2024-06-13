package com.praim.inventory.product.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "product_images")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Product product;

    @Column(nullable = false)
    private String imageUrl;

    // Optional:
    // @Column
    // private int displayOrder; // To control the image order if needed

    // Constructors, Getters, and Setters
}

