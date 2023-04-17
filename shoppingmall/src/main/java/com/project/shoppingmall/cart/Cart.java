package com.project.shoppingmall.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shoppingmall.account.User;
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
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cart_quantity")
    private Integer cartQuantity;

    @Column(name = "cart_price")
    private Integer cartPrice;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
