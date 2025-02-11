package com.cartbackend.Shoppingcart.service.product;

import com.cartbackend.Shoppingcart.config.ShopConfig;
import com.cartbackend.Shoppingcart.dto.ImageDto;
import com.cartbackend.Shoppingcart.dto.ProductDto;
import com.cartbackend.Shoppingcart.exception.ResourceNotFoundException;
import com.cartbackend.Shoppingcart.model.Category;
import com.cartbackend.Shoppingcart.model.Image;
import com.cartbackend.Shoppingcart.model.Product;
import com.cartbackend.Shoppingcart.repository.CategoryRepository;
import com.cartbackend.Shoppingcart.repository.ImageRepository;
import com.cartbackend.Shoppingcart.repository.ProductRepository;
import com.cartbackend.Shoppingcart.request.AddProductRequest;
import com.cartbackend.Shoppingcart.request.ProductUpdateRequest;
import org.antlr.v4.runtime.misc.MultiMap;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    // Explicit constructor to ensure initialization
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, ModelMapper modelMapper1) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

    }

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Product not found");
                        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        if (category != null) {
            existingProduct.setCategory(category);
        }

        return existingProduct;
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndName(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

//    @Override
//    public ProductDto convertToDto(Product product) {
//        ProductDto productDto = modelMapper().map(product, ProductDto.class);
//        List<Image> images = ImageRepository.findByProductId(product.getId());
//        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper().map(images, ImageDto.class)).toList();
//        productDto.setImages(imageDtos);
//        return productDto;
//    }


}

