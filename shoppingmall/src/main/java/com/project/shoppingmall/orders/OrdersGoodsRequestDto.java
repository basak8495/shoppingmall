package com.project.shoppingmall.orders;

import com.project.shoppingmall.goods.Goods;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersGoodsRequestDto {

    private Long id;

    private Integer ordersQuantity;

    private Integer ordersPrice;

    private String state;

    private String claim;

    private Goods goods;

    private Orders orders;


    public OrdersGoods dtoToEntity() {
        OrdersGoods ordersGoods = OrdersGoods.builder()
                .id(id)
                .ordersQuantity(ordersQuantity)
                .ordersPrice(ordersPrice)
                .state(state)
                .claim(claim)
                .goods(goods)
                .orders(orders)
                .build();
        return ordersGoods;
    }

}
