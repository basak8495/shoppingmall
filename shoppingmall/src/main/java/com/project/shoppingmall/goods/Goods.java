package com.project.shoppingmall.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shoppingmall.cart.Cart;
import com.project.shoppingmall.orders.OrdersGoods;
import com.project.shoppingmall.product.Product;
import com.project.shoppingmall.product_option.ProductColor;
import com.project.shoppingmall.product_option.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_color_id")
    @JsonIgnore
    private ProductColor productColor;

    @ManyToOne
    @JoinColumn(name = "product_size_id")
    @JsonIgnore
    private ProductSize productSize;


    @Builder.Default
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersGoods> ordersGoodsList = new ArrayList<>();

}
