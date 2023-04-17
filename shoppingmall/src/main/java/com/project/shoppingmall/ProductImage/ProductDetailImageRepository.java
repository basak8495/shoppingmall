package com.project.shoppingmall.ProductImage;

import com.project.shoppingmall.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailImageRepository extends JpaRepository<ProductDetailImage, Long> {

    List<ProductDetailImage> findByProduct(Product product);
}
