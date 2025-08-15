package com.loith.springhl.controller;

import com.loith.springhl.dto.request.ProductCreateDtoRequest;
import com.loith.springhl.dto.request.ProductUpdateDtoRequest;
import com.loith.springhl.dto.response.Product;
import com.loith.springhl.projection.ProductCategoryProjection;
import com.loith.springhl.service.ProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
  //    @Autowired
  //    ProductService productService;
  //    public ProductController(ProductService productService) {
  //        this.productService = productService;
  //    }
  private final ProductService productService;

  //  parse form-data (text + file) vào DTO
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public Product createProduct(@ModelAttribute ProductCreateDtoRequest productCreateDtoRequest) {
    return productService.createProduct(productCreateDtoRequest);
  }

  @GetMapping
  public List<ProductCategoryProjection> getAllProducts() {
    return productService.getAll();
  }

//  @GetMapping(value = "{id}")
//  public Product getById(@PathVariable UUID id) {
//    return productService.getById(id);
//  }
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
