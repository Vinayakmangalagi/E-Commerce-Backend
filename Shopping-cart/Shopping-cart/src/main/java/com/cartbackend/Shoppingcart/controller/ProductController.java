package com.cartbackend.Shoppingcart.controller;

import com.cartbackend.Shoppingcart.exception.ResourceNotFoundException;
import com.cartbackend.Shoppingcart.model.Product;
import com.cartbackend.Shoppingcart.request.AddProductRequest;
import com.cartbackend.Shoppingcart.request.ProductUpdateRequest;
import com.cartbackend.Shoppingcart.response.ApiResponse;
import com.cartbackend.Shoppingcart.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(value = "${api.prefix}/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/all")
    public ResponseEntity<ApiResponse> getAllProduct(){
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.ok(new ApiResponse("success",products));
    }

    @GetMapping(value = "product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product products = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("success",products));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
@PostMapping(value = "/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Success",theProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping(value = "/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable Long productId){
        try {
            Product thProduct = productService.updateProduct(request,productId);
            return ResponseEntity.ok(new ApiResponse("update product success!",thProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping(value = "/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success",productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping(value = "/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName,@PathVariable String productName){
        try {
            List<Product> products =productService.getProductByBrandAndName(brandName,productName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found with name"+productName,null));
            }
            return ResponseEntity.ok(new ApiResponse("Success",products));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping(value = "/product/by/category-and-name")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category,@RequestParam String brandName){
        try {
            List<Product> products =productService.getProductByCategoryAndBrand(category, brandName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found ",null));
            }
            return ResponseEntity.ok(new ApiResponse("Success",products));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }

    }

    @GetMapping(value = "/product/{name}/product")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product> products =productService.getProductByName(name);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found with name",null));
            }
            return ResponseEntity.ok(new ApiResponse("Success",products));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("error",e.getMessage()));
        }

    }

    @GetMapping(value = "/product/by-brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
        try {
            List<Product> products =productService.getProductByBrand(brand);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found with name",null));
            }
            return ResponseEntity.ok(new ApiResponse("Success",products));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping(value = "/product/{category}/all/product")
    public ResponseEntity<ApiResponse> findProductByCategory(@RequestParam String category){
        try {
            List<Product> products =productService.getProductsByCategory(category);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found with name",null));
            }
            return ResponseEntity.ok(new ApiResponse("Success",products));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping(value = "/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand,@RequestParam String name){
        try {
            var productCount =productService.countProductByBrandAndName(brand,name);
            return ResponseEntity.ok(new ApiResponse("Success",productCount));
        } catch (Exception e){
            return ResponseEntity.ok(new ApiResponse(e.getMessage(),null));
        }

    }

}
