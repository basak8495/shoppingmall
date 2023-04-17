package com.project.shoppingmall.cart;

import com.project.shoppingmall.account.User;
import com.project.shoppingmall.goods.Goods;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDto {

    private Long id;

    private Integer cartQuantity;

    private Integer cartPrice;

    private Goods goods;

    private User user;


    public Cart toEntity() {
        Cart cart = Cart.builder()
                .id(id)
                .cartQuantity(cartQuantity)
                .cartPrice(cartPrice)
                .goods(goods)
                .user(user)
                .build();
        return cart;
    }

}
