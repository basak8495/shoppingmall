package com.project.shoppingmall.product_image;

import com.project.shoppingmall.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductMainImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    private String uuid;

    @Column(name = "img_name")
    private String imgName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
