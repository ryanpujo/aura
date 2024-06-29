package com.praim.inventory.product.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.praim.inventory.product.dtos.ProductVariantDTO;
import com.praim.inventory.product.entities.ProductInventory;
import com.praim.inventory.product.mappers.ProductVariantMapper;
import com.praim.inventory.product.repositories.ProductInventoryRepo;
import com.praim.inventory.warehouse.entities.Warehouse;
import com.praim.inventory.warehouse.repositories.WarehouseRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
  private WarehouseRepo warehouseRepo;

  @Mock
  private ProductInventoryRepo inventoryRepo;

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
  private static final Warehouse warehouse = new Warehouse();
  private static ProductInventory productInventory;

  public static void setUp() {
    dto.setName("Awesome Gadget");
    dto.setDescription("A fantastic new gadget that will change your life.");
    dto.setPrice(new BigDecimal("49.99"));
    dto.setVariants(variants);
    dto.setSKU("3345667");
    dto.setImageUrl("https://example.com/gadget.jpg");
    dto.setCategories(categories);
    dto.setImages(images);
    tesProduct = ProductMapper.INSTANCE.toEntity(dto);
    productInventory = ProductInventory.builder()
            .product(tesProduct)
            .warehouse(warehouse)
            .totalStock(50)
            .productVariants(ProductVariantMapper.INSTANCE.toVariants(variants))
            .build();
  }

  static Stream<Arguments> saveSourceData() {
    setUp();
    return Stream.of(
      Arguments.of(
              dto,
              productInventory,
              Optional.of(warehouse),
              Optional.of(tesProduct),
              null),
      Arguments.of(
              dto,
              productInventory,
              Optional.of(warehouse),
              Optional.empty(),
              null
      ),
      Arguments.of(
              dto,
              null,
              Optional.empty(),
              Optional.empty(),
              NotFoundException.class)
    );
  }

  @ParameterizedTest
  @MethodSource("saveSourceData")
  public void givenProduct_WhenSave_ThenReturnProductOrThrows(
    ProductDTO arg, 
    ProductInventory exProduct,
    Optional<Warehouse> expectedWarehouse,
    Optional<Product> expectedProduct,
    Class<? extends Exception> expectedException
    ) {
    when(warehouseRepo.findById(1L)).thenReturn(expectedWarehouse);
    lenient().when(productRepo.save(any())).thenReturn(tesProduct);
    if (expectedWarehouse.isPresent()) {
      when(productRepo.findBySKU(anyString())).thenReturn(expectedProduct);
      when(inventoryRepo.save(any())).thenReturn(exProduct);
    }

    if (expectedException == null) {
      var product = productService.save(arg, 1);
      assertEquals(tesProduct, product);
      verify(inventoryRepo, times(1)).save(exProduct);
      if (expectedProduct.isPresent()) {
        verify(productRepo, never()).save(tesProduct);
      } else {
        verify(productRepo, times(1)).save(tesProduct);
      }
    }  else {
      var result = assertThrows(expectedException, () -> productService.save(arg, 1));
      assertEquals(String.format("warehouse with id %d is not found", 1), result.getMessage());
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
      verify(productRepo, times(1)).findById(id);
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
