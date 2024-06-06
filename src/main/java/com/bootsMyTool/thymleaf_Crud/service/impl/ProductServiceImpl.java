package com.bootsMyTool.thymleaf_Crud.service.impl;

import com.bootsMyTool.thymleaf_Crud.dto.ProductDto;
import com.bootsMyTool.thymleaf_Crud.entity.Product;
import com.bootsMyTool.thymleaf_Crud.repository.ProductRepository;
import com.bootsMyTool.thymleaf_Crud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }


    @Override
    public void saveProduct(ProductDto productDto) {
        MultipartFile image=productDto.getImageFile();

        Date createDate = new Date();
        String storageFileName = createDate.getTime() +"_"+image.getOriginalFilename();
        try {
            String uploadDir="public/images/";
            Path uploadPath= Paths.get(uploadDir);

            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream,Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);

        }catch (Exception e){
            System.out.println("Exception: "+e.getMessage());
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setBrand(productDto.getBrand());
        product.setCreatedAt(createDate);
        product.setImageFileName(storageFileName);

        productRepository.save(product);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product findProductById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).get();
//delete image

            String uploadDir="public/images";
            Path oldImagePath= Paths.get(uploadDir + product.getImageFileName());
            try {
                Files.delete(oldImagePath);

            }catch (Exception e){
                System.out.println("Exception :" + e.getMessage());
            }

        productRepository.delete(product);
    }


}
