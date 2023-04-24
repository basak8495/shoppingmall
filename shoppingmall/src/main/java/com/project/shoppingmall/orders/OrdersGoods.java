package com.project.shoppingmall.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shoppingmall.goods.Goods;
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
public class OrdersGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orders_quantity")
    private Integer ordersQuantity;

    @Column(name = "orders_price")
    private Integer ordersPrice;

    private String state;

    private String claim;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    @JsonIgnore
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    @JsonIgnore
    private Orders orders;
}
