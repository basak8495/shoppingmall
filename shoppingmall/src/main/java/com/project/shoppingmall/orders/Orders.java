package com.project.shoppingmall.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.shoppingmall.account.User;
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
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private Integer totalPrice;

    private String date;

    private String num;

    @Column(name = "orderer_name")
    private String ordererName;

    @Column(name = "orderer_email")
    private String ordererEmail;

    @Column(name = "orderer_phone")
    private String ordererPhone;

    @Column(name = "delivery_name")
    private String deliveryName;

    @Column(name = "delivery_postal_code")
    private String deliveryPostalCode;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_extra_address")
    private String deliveryExtraAddress;

    @Column(name = "delivery_detail_address")
    private String deliveryDetailAddress;

    @Column(name = "delivery_phone")
    private String deliveryPhone;

    @Column(name = "delivery_request")
    private String deliveryRequest;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


    @Builder.Default
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrdersGoods> ordersGoods = new ArrayList<>();

}
