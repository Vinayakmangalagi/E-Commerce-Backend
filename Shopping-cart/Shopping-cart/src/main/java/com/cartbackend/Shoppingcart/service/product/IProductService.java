package com.cartbackend.Shoppingcart.service.product;

import com.cartbackend.Shoppingcart.dto.ProductDto;
import com.cartbackend.Shoppingcart.model.Product;
import com.cartbackend.Shoppingcart.request.AddProductRequest;
import com.cartbackend.Shoppingcart.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product> getAllProduct();
    List<Product> getProductsByCategory(String  category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category,String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand,String name);
    Long countProductByBrandAndName(String brand,String name);


//    ProductDto convertToDto(Product product);
}
