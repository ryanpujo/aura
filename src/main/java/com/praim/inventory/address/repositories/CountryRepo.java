package com.praim.inventory.address.repositories;

import com.praim.inventory.address.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
}
