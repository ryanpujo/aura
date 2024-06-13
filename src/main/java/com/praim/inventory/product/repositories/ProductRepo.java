package com.praim.inventory.product.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.praim.inventory.product.entities.Product;
import java.util.List;


@Repository
public interface ProductRepo extends CrudRepository<Product, Long>{
  public List<Product> findByName(String name);
}
