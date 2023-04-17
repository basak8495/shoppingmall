package com.project.shoppingmall.product_option;

import com.project.shoppingmall.product_option.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {

    ProductSize findBySize(String size);

}