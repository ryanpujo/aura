package com.praim.inventory.product.services;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.praim.inventory.common.exceptions.NotFoundException;
import com.praim.inventory.product.dtos.ProductDTO;
import com.praim.inventory.product.entities.Product;
import com.praim.inventory.product.entities.ProductCategory;
import com.praim.inventory.product.entities.ProductImage;
import com.praim.inventory.product.mappers.ProductMapper;
import com.praim.inventory.product.repositories.CategoryRepo;
import com.praim.inventory.product.repositories.ProductRepo;


import java.util.Optional;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {

  private final ProductRepo productRepo;
  private final CategoryRepo categoryRepo;

  /**
   * Constructor for ProductService.
   *
   * @param productRepo  Repository for product data access.
   * @param categoryRepo Repository for category data access.
   */
  public ProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
    this.productRepo = productRepo;
    this.categoryRepo = categoryRepo;
  }

  /**
   * Saves a product to the repository.
   *
   * @param dto Data transfer object containing product information.
   * @return The saved product entity.
   */
  public Product save(ProductDTO dto) {
    Product product = ProductMapper.INSTANCE.toEntity(dto);
    List<ProductCategory> categories = mapCategories(product.getCategories());
    linkImagesToProduct(product.getImages(), product);
    product.getProductVariants().forEach(variant -> variant.setProduct(product));
    product.setCategories(categories);
    return productRepo.save(product);
  }

  /**
   * Finds a product by its ID.
   *
   * @param id The ID of the product.
   * @return The found product.
   * @throws NotFoundException if the product is not found.
   */
  public Product findByID(Long id) {
    return productRepo.findById(id)
      .orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id)));
  }

  /**
   * Retrieves all products from the repository.
   *
   * @return A list of all products.
   */
  public List<Product> findAll() {
    List<Product> products = new ArrayList<>();
    productRepo.findAll().forEach(products::add);
    return products;
  }

  /**
   * Maps category DTOs to entities and retrieves existing categories.
   *
   * @param categories The list of category DTOs.
   * @return A list of category entities.
   */
  private List<ProductCategory> mapCategories(List<ProductCategory> categories) {
    List<ProductCategory> categoriesFound = new ArrayList<>();
    categories.forEach(category -> {
      Optional<ProductCategory> found = categoryRepo.findByName(category.getName());
      categoriesFound.add(found.orElse(category));
    });
    return categoriesFound;
  }

  /**
   * Links images to a product.
   *
   * @param images  The list of product images.
   * @param product The product to link to.
   */
  private void linkImagesToProduct(List<ProductImage> images, Product product) {
    images.forEach(image -> image.setProduct(product));
  }
}

