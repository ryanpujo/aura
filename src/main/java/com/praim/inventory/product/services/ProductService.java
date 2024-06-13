package com.praim.inventory.product.services;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.praim.inventory.common.exceptions.NotFoundException;
import com.praim.inventory.product.dtos.ProductDTO;
import com.praim.inventory.product.entities.Product;
import com.praim.inventory.product.mappers.ProductCategoryMapper;
import com.praim.inventory.product.mappers.ProductImageMapper;
import com.praim.inventory.product.mappers.ProductMapper;
import com.praim.inventory.product.repositories.ProductRepo;

@Service
public class ProductService {
  
  private ProductRepo productRepo;

  public ProductService(ProductRepo productRepo) {
    this.productRepo = productRepo;
  }

  public Product save(ProductDTO dto) {
    var images = ProductImageMapper.INSTANCE.toEntityList(dto.getImages());
    var categories = ProductCategoryMapper.INSTANCE.toEntityList(dto.getCategories());
    var product = ProductMapper.INSTANCE.toEntity(dto);
    images.forEach((image) -> image.setProduct(product));
    product.setCategories(categories);
    product.setImages(images);
    return productRepo.save(product);
  }

  public Product findByID(Long id) {
    return productRepo.findById(id)
      .orElseThrow(() -> new NotFoundException(String.format("user with id %d not found", id)));
  }

  public List<Product> findAll() {
	  var products = new ArrayList<Product>();
	  productRepo.findAll().forEach(products::add);
	  return products;
  }
}
