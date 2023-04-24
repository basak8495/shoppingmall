package com.project.shoppingmall.cart;


import com.project.shoppingmall.account.User;
import com.project.shoppingmall.goods.Goods;
import com.project.shoppingmall.product_image.ProductMainImageDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {

    private Long id;

    private Integer cartQuantity;

    private Integer cartPrice;

    private Goods goods;

    private User user;

    @Builder.Default
    private List<ProductMainImageDto> productMainImageDtoList = new ArrayList<>();
}
