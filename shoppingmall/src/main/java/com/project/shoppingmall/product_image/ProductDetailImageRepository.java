package com.project.shoppingmall.product_image;

import com.project.shoppingmall.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailImageRepository extends JpaRepository<ProductDetailImage, Long> {

    List<ProductDetailImage> findByProduct(Product product);
}
