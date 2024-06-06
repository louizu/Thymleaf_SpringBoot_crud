package com.bootsMyTool.thymleaf_Crud.service;

import com.bootsMyTool.thymleaf_Crud.dto.ProductDto;
import com.bootsMyTool.thymleaf_Crud.entity.Product;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface ProductService {

    List<Product> findAll();
    void saveProduct(ProductDto productDto);
    void saveProduct(Product product);
    Product findProductById(int id);
    void deleteProduct(int id);
}
