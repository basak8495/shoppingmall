package com.project.shoppingmall.product_image;

import com.project.shoppingmall.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMainImageRepository extends JpaRepository<ProductMainImage, Long> {

    List<ProductMainImage> findByProduct(Product product);
}
