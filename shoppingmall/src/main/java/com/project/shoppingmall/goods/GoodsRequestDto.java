package com.project.shoppingmall.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shoppingmall.product.Product;
import com.project.shoppingmall.product_option.ProductColor;
import com.project.shoppingmall.product_option.ProductSize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class GoodsRequestDto {

    private Integer stock;

    private Product product;

    private ProductColor productColor;

    private ProductSize productSize;


    public Goods toEntity() {
        Goods goods = Goods.builder()
                .product(product)
                .productColor(productColor)
                .productSize(productSize)
                .stock(stock)
                .build();
        return goods;
    }

}
