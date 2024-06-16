package com.praim.inventory.address.repositories;

import com.praim.inventory.address.entities.StateProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateProvinceRepo extends JpaRepository<StateProvince, Long> {
    Optional<StateProvince> findByName(String name);
}
