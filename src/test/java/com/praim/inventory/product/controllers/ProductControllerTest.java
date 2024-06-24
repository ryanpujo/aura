package com.praim.inventory.product.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.stream.Stream;

import com.praim.inventory.common.exceptions.NotFoundException;
import com.praim.inventory.product.dtos.ProductDTO;
import com.praim.inventory.product.entities.Product;
import com.praim.inventory.product.mappers.ProductMapper;
import com.praim.inventory.product.services.ProductService;


// @ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {
  
  @MockBean
  private ProductService mProductService;

  @Autowired
  private MockMvc mockMvc;

    private static Product tesProduct;

    public static void setUp() {
      ProductDTO productDTO = new ProductDTO();

    productDTO.setName("Awesome Gadget");
    productDTO.setDescription("A fantastic new gadget that will change your life.");
    productDTO.setPrice(new BigDecimal("49.99"));
    productDTO.setImageUrl("https://example.com/gadget.jpg");

    tesProduct = ProductMapper.INSTANCE.toEntity(productDTO);
    tesProduct.setId(1L);
      System.out.println("inside set up " + tesProduct);

  }

  public static Stream<Arguments> createProductSource() {
    setUp();
      String tResponseEntity = String.format("http://localhost/products/%d", 1);

      String badJsonString = """
              {
                "description": "This is a sample product.",
                "price": 99.99,
                "stock": 50,
                "image_url": "https://example.com/product.jpg",
                "images": [
                  {
                    "image_url": "https://example.com/image1.jpg"
                  },
                  {
                    "image_url": "https://example.com/image2.jpg"
                  }
                ],
                "categories": [
                  {
                    "name": "Electronics"
                  },
                  {
                    "name": "Gadgets"
                  }
                ]
              }
              """;
      String jsonString = """
              {
                "name": "Sample Product",
                "description": "This is a sample product.",
                "price": 99.99,
                "image_url": "https://example.com/product.jpg",
                "images": [
                  {
                    "image_url": "https://example.com/image1.jpg"
                  },
                  {
                    "image_url": "https://example.com/image2.jpg"
                  }
                ],
                "categories": [
                  {
                    "name": "Electronics"
                  },
                  {
                    "name": "Gadgets"
                  }
                ],
                "variants": [
                  {
                    "size": "50x50",
                    "color": "White",
                    "additional_price": 20,
                    "stock": 10
                  },
                  {
                    "size": "50x50",
                    "color": "Black",
                    "additional_price": 10,
                    "stock": 10
                  }
                ]
              }
              """;
      return Stream.of(
      Arguments.of(jsonString, header().string("Location", Matchers.endsWith(tResponseEntity)), status().isCreated()),
      Arguments.of(badJsonString, header().string("Location", Matchers.blankOrNullString()), status().isBadRequest())
    );
  }
  @ParameterizedTest
  @MethodSource("createProductSource")
  public void givenProduct_WhenCreateProduct_ShouldResponse(
    String json, 
    ResultMatcher exLocation,
    ResultMatcher exStatusCode) throws Exception {
    when(mProductService.save(any(), anyLong())).thenReturn(tesProduct);

    mockMvc.perform(post("/products?warehouse=1")
      .content(json) 
      .contentType(MediaType.APPLICATION_JSON)
    ).andExpect(exStatusCode)
    .andExpect(exLocation);
  }


  private static final String errMessage = String.format("Product with id %d is not available", 1);
  public static Stream<Arguments> findByIDSource() {
    setUp();
    return Stream.of(
      Arguments.of(1L, status().isOk(), jsonPath("$.name").value(tesProduct.getName()), tesProduct),
      Arguments.of(1L, status().isNotFound(), jsonPath("$.message").value(errMessage), null)
    );
  }
  
  @ParameterizedTest
  @MethodSource("findByIDSource")
  public void givenId_WhenFindById_ThenReturnOrThrows(
    Long id, 
    ResultMatcher exCode, 
    ResultMatcher exJson,
    Product exReturn
  ) throws Exception {
    when(mProductService.findByID(anyLong())).thenAnswer((invoc) -> {
      if (exReturn == null) {
        throw new NotFoundException(errMessage);
      }

      return exReturn;
    });

    mockMvc.perform(
      get("/products/1").contentType(MediaType.APPLICATION_JSON)
    ).andExpect(exJson)
    .andExpect(exCode);

    Mockito.verify(mProductService, times(1)).findByID(id);
  }
}
