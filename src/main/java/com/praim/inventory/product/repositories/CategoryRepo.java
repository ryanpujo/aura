package com.praim.inventory.product.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.praim.inventory.product.entities.ProductCategory;
import java.util.Optional;

//Remove @RepositoryRestResource below to disable auto REST api:
@Repository
public interface CategoryRepo extends CrudRepository<ProductCategory, Long>{
  public Optional<ProductCategory> findByName(String name);
}
