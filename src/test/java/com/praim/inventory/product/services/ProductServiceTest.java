package com.praim.inventory.product.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.praim.inventory.product.dtos.ProductVariantDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.praim.inventory.common.exceptions.NotFoundException;
import com.praim.inventory.product.dtos.ProductCategoryDTO;
import com.praim.inventory.product.dtos.ProductDTO;
import com.praim.inventory.product.dtos.ProductImageDTO;
import com.praim.inventory.product.entities.Product;
import com.praim.inventory.product.mappers.ProductMapper;
import com.praim.inventory.product.repositories.CategoryRepo;
import com.praim.inventory.product.repositories.ProductRepo;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock
  private ProductRepo productRepo;

  @Mock
  private CategoryRepo categoryRepo;

  @InjectMocks
  private ProductService productService;

  private static final List<ProductImageDTO> images = Arrays.asList(
    new ProductImageDTO("image"),
    new ProductImageDTO("image1")
  );

  private static final List<ProductCategoryDTO> categories = Arrays.asList(
    new ProductCategoryDTO("gadget"),
    new ProductCategoryDTO("gadget1")
  );

  private static final List<ProductVariantDTO> variants = List.of(
          ProductVariantDTO.builder().size("50x50").stock(50)
                  .color("Black Mate").additionalPrice(new BigDecimal(20)).build()
  );

  private static final ProductDTO dto = new ProductDTO();
  private static Product tesProduct;

  public static void setUp() {
    dto.setName("Awesome Gadget");
    dto.setDescription("A fantastic new gadget that will change your life.");
    dto.setPrice(new BigDecimal("49.99"));
    dto.setVariants(variants);
    dto.setImageUrl("https://example.com/gadget.jpg");
    dto.setCategories(categories);
    dto.setImages(images);
    tesProduct = ProductMapper.INSTANCE.toEntity(dto);
  }

  static Stream<Arguments> saveSourceData() {
    setUp();
    return Stream.of(
      Arguments.of(dto, tesProduct, null),
      Arguments.of(dto, null, IllegalArgumentException.class)
    );
  }

  @ParameterizedTest
  @MethodSource("saveSourceData")
  public void givenProduct_WhenSave_ThenReturnProductOrThrows(
    ProductDTO arg, 
    Product exProduct, 
    Class<? extends Exception> expectedException
    ) {
    when(productRepo.save(tesProduct)).thenAnswer((invocation) -> {
      if (exProduct == null) {
        throw new IllegalArgumentException("data invalid");
      }
      return exProduct;
    });

    if (expectedException == null) {
      var product = productService.save(arg);
      assertEquals(exProduct, product);
      Mockito.verify(productRepo, times(1)).save(tesProduct);
    } else {
      assertThrows(expectedException, () -> productService.save(arg));
    }
  }

  public static Stream<Arguments> findByIdSourceData() {
    setUp();
    return Stream.of(
      Arguments.of(3L, Optional.of(tesProduct), null),
      Arguments.of(3L, Optional.empty(), NotFoundException.class)
    );
  }

  @ParameterizedTest
  @MethodSource("findByIdSourceData")
  public void givenId_WhenFindById_ThenReturnProductOrThrow(Long id, Optional<Product> exProduct,
   Class<? extends Exception> exException) {
    when(productRepo.findById(id)).thenReturn(exProduct);

    if (exException == null) {
      var product = productService.findByID(id);

      assertEquals(exProduct.get(), product);
      Mockito.verify(productRepo, times(1)).findById(id);
    } else {
      var exMessage = assertThrows(exException, () -> productService.findByID(id));
      assertEquals("Product with id 3 not found", exMessage.getMessage());
    }
  }

  @Test
  public void whenFindAll_ThenReturn() {
    var products = Arrays.asList(tesProduct, tesProduct);
    when(productRepo.findAll()).thenReturn(products);

    var actual = productService.findAll();

    assertNotNull(actual);
    assertEquals(products, actual);
  }
}
