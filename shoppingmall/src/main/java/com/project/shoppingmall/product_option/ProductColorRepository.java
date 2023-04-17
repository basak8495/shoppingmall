package com.project.shoppingmall.product_option;

import com.project.shoppingmall.product_option.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {

    ProductColor findByColor(String color);

}