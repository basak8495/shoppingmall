package com.project.shoppingmall.product;

import com.project.shoppingmall.ProductImage.ProductDetailImageDto;
import com.project.shoppingmall.ProductImage.ProductMainImageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductRequestDto {

    private String category;

    private String subCategoryId;

    private String subCategory;

    private String name;

    private Integer price;

    private List<ProductMainImageDto> productMainImageDtoList = new ArrayList<>();

    private List<ProductDetailImageDto> productDetailImageDtoList = new ArrayList<>();

}
