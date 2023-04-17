package com.project.shoppingmall.orders;

import com.project.shoppingmall.goods.Goods;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersGoodsDto {

    private Long id;

    private Integer ordersQuantity;

    private Integer ordersPrice;

    private String state;

    private String claim;

    private Goods goods;

    private Orders orders;
}
