package com.cartbackend.Shoppingcart.service.image;

import com.cartbackend.Shoppingcart.dto.ImageDto;
import com.cartbackend.Shoppingcart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageBuId(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
    void upDateImage(MultipartFile file, Long imageId);
}
