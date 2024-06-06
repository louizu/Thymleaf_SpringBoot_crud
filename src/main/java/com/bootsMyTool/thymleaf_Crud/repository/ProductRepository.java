package com.bootsMyTool.thymleaf_Crud.repository;

import com.bootsMyTool.thymleaf_Crud.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
