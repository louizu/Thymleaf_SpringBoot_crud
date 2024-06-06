package com.bootsMyTool.thymleaf_Crud.controller;


import com.bootsMyTool.thymleaf_Crud.dto.ProductDto;
import com.bootsMyTool.thymleaf_Crud.entity.Product;
import com.bootsMyTool.thymleaf_Crud.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Controller
@RequestMapping("/products")
public class ProductController {
@Autowired
private ProductServiceImpl productService;

    @GetMapping({"" , "/"})
    public String productsList(Model model){
     model.addAttribute("products",productService.findAll());
     return "products/index";
    }


    @GetMapping("/create")
    public String showCreatePage(Model model){
        model.addAttribute("productDto", new ProductDto());
        return "products/createProduct";
    }


    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result){

        if(productDto.getImageFile().isEmpty()){
            result.addError(new FieldError("productDto","imageFile","the image file is required"));
        }

        if(result.hasErrors()){
            return "products/createProduct";
        }
        productService.saveProduct(productDto);
        return "redirect:/products";

    }


    @GetMapping("/edit")
    public String showEditPage(Model model ,@RequestParam int id){
        try {
            Product product=productService.findProductById(id);
            model.addAttribute("product",product);

            ProductDto productDto=new ProductDto();
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setCategory(product.getCategory());
            productDto.setBrand(product.getBrand());

            model.addAttribute("productDto",productDto);

        }
        catch (Exception e){
            System.out.println("Exception :" + e.getMessage());
            return "redirect:/products";

        }


     return "products/editProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(Model model ,@RequestParam int id, @Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result){
        try {
            Product product=productService.findProductById(id);
            model.addAttribute("product",product);

            if(result.hasErrors()){
                return "products/editProduct";
            }


            //verifye if old image existe
            if(!productDto.getImageFile().isEmpty()){
                String uploadDir="public/images";
                Path oldImagePath= Paths.get(uploadDir + product.getImageFileName());
                try {
                    Files.delete(oldImagePath);

                }catch (Exception e){
                    System.out.println("Exception :" + e.getMessage());
                }
            }

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

                product.setImageFileName(storageFileName);
                product.setName(productDto.getName());
                product.setDescription(productDto.getDescription());
                product.setPrice(productDto.getPrice());
                product.setCategory(productDto.getCategory());
                product.setBrand(productDto.getBrand());
                productService.saveProduct(product);

            }catch (Exception e){
                System.out.println("Exception: "+e.getMessage());
            }



        }
        catch (Exception e){
            System.out.println("Exception :" + e.getMessage());

        }

        return "redirect:/products";

    }


@GetMapping("/delete")
    public String deleteProduct(@RequestParam int id){
    productService.deleteProduct(id);
return "redirect:/products";
    }






}
