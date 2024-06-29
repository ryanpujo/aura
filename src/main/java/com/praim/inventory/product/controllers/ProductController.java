package com.praim.inventory.product.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.praim.inventory.product.dtos.ProductDTO;
import com.praim.inventory.product.entities.Product;
import com.praim.inventory.product.mappers.ProductCategoryMapper;
import com.praim.inventory.product.mappers.ProductImageMapper;
import com.praim.inventory.product.mappers.ProductMapper;
import com.praim.inventory.product.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;

import java.util.*;



@RestController
@RequestMapping("/products")
public class ProductController {
  
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(
          @Valid @RequestBody ProductDTO productDTO,
          @RequestParam("warehouse") long id) {
    var productCreated = productService.save(productDTO, id);
    var uri = ServletUriComponentsBuilder.fromCurrentRequest().replaceQuery(null)
      .path("/{id}")
      .buildAndExpand(productCreated.getId())
      .toUri();
    return ResponseEntity.created(uri).build();
  }

  @GetMapping("/{id}")
  public EntityModel<ProductDTO> findByID(@PathVariable("id") Long id) {
    var product = productService.findByID(id);
    var dto = ProductMapper.INSTANCE.toDTO(product);
    var productEntity = EntityModel.of(dto);
    var link = linkTo(methodOn(this.getClass()).findAll());
    productEntity.add(link.withRel("all-products"));
    return productEntity;
  }

  @GetMapping()
  public List<Product> findAll() {
    return productService.findAll();
  }
  
  
}
