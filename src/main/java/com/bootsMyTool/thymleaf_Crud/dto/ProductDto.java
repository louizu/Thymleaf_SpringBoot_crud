package com.bootsMyTool.thymleaf_Crud.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import java.util.Date;

public class ProductDto {
    @NotEmpty(message = "the name is required")
    private String name;

    @NotEmpty(message = "the brand is required")
    private String brand;

    @NotEmpty(message = "the category is required")
    private String category;

    @Min(0)
    private double price;

    @Size(min = 10 , message = "the description should be at least 10 characters")
    @Size(max = 20 , message = "the description can not exceed 20 characters")
    private String description;
    private Date createdAt;
    private MultipartFile imageFile;



    public @NotEmpty(message = "the name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "the name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "the brand is required") String getBrand() {
        return brand;
    }

    public void setBrand(@NotEmpty(message = "the brand is required") String brand) {
        this.brand = brand;
    }

    public @NotEmpty(message = "the category is required") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "the category is required") String category) {
        this.category = category;
    }

    @Min(0)
    public double getPrice() {
        return price;
    }

    public void setPrice(@Min(0) double price) {
        this.price = price;
    }

    public @Size(min = 10, message = "the description should be at least 10 characters") @Size(max = 20, message = "the description can not exceed 20 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "the description should be at least 10 characters") @Size(max = 20, message = "the description can not exceed 20 characters") String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
