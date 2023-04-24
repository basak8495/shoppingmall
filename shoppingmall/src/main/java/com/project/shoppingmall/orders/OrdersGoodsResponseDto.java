package com.project.shoppingmall.orders;

import com.project.shoppingmall.goods.Goods;
import lombok.*;

@Getter
@Setter
public class OrdersGoodsResponseDto {

    private Long id;

    private Integer ordersQuantity;

    private Integer ordersPrice;

    private String state;

    private String claim;

    private Goods goods;

    private Orders orders;


    public OrdersGoodsResponseDto(OrdersGoods ordersGoods) {
        this.id = ordersGoods.getId();
        this.ordersQuantity = ordersGoods.getOrdersQuantity();
        this.ordersPrice = ordersGoods.getOrdersPrice();
        this.state = ordersGoods.getState();
        this.claim = ordersGoods.getClaim();
        this.goods = ordersGoods.getGoods();
        this.orders = ordersGoods.getOrders();
    }
}
