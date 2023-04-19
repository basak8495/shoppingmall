package com.project.shoppingmall.product;

import com.project.shoppingmall.product_image.ProductDetailImageDto;
import com.project.shoppingmall.product_image.ProductMainImageDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;

    private String category;

    private String subCategoryId;

    private String subCategory;

    private String name;

    private Integer price;

    @Builder.Default
    private List<ProductMainImageDto> productMainImageDtoList = new ArrayList<>();

    @Builder.Default
    private List<ProductDetailImageDto> productDetailImageDtoList = new ArrayList<>();


    public ProductResponseDto(Product product) {
        this.category = product.getCategory();
        this.subCategoryId = product.getSubCategoryId();
        this.subCategory = product.getSubCategory();
        this.name = product.getName();
        this.price = product.getPrice();
    }

/*    public ProductResponseDto toDto(Product product) {
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .category(category)
                .subCategoryId(product.getSubCategoryId())
                .subCategory(product.getSubCategory())
                .name(product.getName())
                .price(product.getPrice())
                .build();
        return productResponseDto;
    }*/
}
