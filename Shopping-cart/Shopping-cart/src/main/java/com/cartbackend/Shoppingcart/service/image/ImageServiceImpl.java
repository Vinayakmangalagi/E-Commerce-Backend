package com.cartbackend.Shoppingcart.service.image;

import com.cartbackend.Shoppingcart.dto.ImageDto;
import com.cartbackend.Shoppingcart.exception.ResourceNotFoundException;
import com.cartbackend.Shoppingcart.model.Image;
import com.cartbackend.Shoppingcart.model.Product;
import com.cartbackend.Shoppingcart.repository.ImageRepository;
import com.cartbackend.Shoppingcart.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService{
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No image found with id:"+id));
    }

    @Override
    public void deleteImageBuId(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new ResourceNotFoundException("No image found with id:"+id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> file, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savesImageDto = new ArrayList<>();
        for (MultipartFile file1: file){
            try {
                Image image = new Image();
                image.setFilename(file1.getOriginalFilename());
                image.setFilename(file1.getContentType());
                image.setImage(new SerialBlob(file1.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download";
                String downloadUrl = buildDownloadUrl +image.getId();
                image.setDownloadUrl(downloadUrl);
               Image savedImage = imageRepository.save(image);

               savedImage.setDownloadUrl(buildDownloadUrl +savedImage.getId());
               imageRepository.save(savedImage);

               ImageDto imageDto1 = new ImageDto();
               imageDto1.setImageId(savedImage.getId());
               imageDto1.setImageName(savedImage.getFilename());
               imageDto1.setDownloadUrl(savedImage.getDownloadUrl());
                savesImageDto.add(imageDto1);
            }catch (IOException | SQLException exception){
                throw new RuntimeException(exception.getMessage());
            }
        }
        return savesImageDto;
    }

    @Override
    public void upDateImage(MultipartFile file, Long imageId) {
        Image image= getImageById(imageId);
        try {
            image.setFilename(file.getOriginalFilename());
            image.setFilename(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
