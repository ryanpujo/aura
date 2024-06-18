package com.praim.inventory.address.repositories;

import com.praim.inventory.address.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {
    Optional<City> findByNameAndStateProvinceId(String cityName, long stateID);
}
