package com.loith.springhl.controller;

import com.loith.springhl.dto.request.ProductCreateDtoRequest;
import com.loith.springhl.dto.response.Product;
import com.loith.springhl.service.ProductService;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management")
public class ProductController {
  private final ProductService productService;

  @Operation(summary = "Create a new product", description = "Create a new product with provided details")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "Product created successfully"),
          @ApiResponse(responseCode = "400", description = "Invalid input data"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Product createProduct(@RequestBody ProductCreateDtoRequest productCreateDtoRequest) {
    return productService.createProduct(productCreateDtoRequest);
  }

  @Operation(summary = "Get all products", description = "Retrieve a list of all available products")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved product list"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  public List<Product> getAllProducts() {
    return productService.getAll();
  }

  //    @GetMapping(value = "{id}")
  //    public Product getById(@PathVariable UUID id) {
  //      return productService.getById(id);
  //    }
  //
  //  @DeleteMapping(value = "{id}")
  //  @ResponseStatus(HttpStatus.NO_CONTENT)
  //  public void deleteAllProducts(@PathVariable UUID id) {
  //    productService.deleteById(id);
  //  }
  //
  //  @PutMapping(value = "{uuid}")
  //  public Product updateProduct(
  //      @PathVariable UUID uuid, @RequestBody ProductUpdateDtoRequest productUpdateDtoRequest) {
  //    return productService.updateProduct(uuid, productUpdateDtoRequest);
  //  }
}
