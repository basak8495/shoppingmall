package com.project.shoppingmall.orders;

import com.project.shoppingmall.account.User;
import lombok.*;

@Getter
@Setter
public class OrdersResponseDto {

    private Long id;

    private Integer totalPrice;

    private String date;

    private String num;

    private String ordererName;

    private String ordererEmail;

    private String ordererPhone;

    private String deliveryName;

    private String deliveryPostalCode;

    private String deliveryAddress;

    private String deliveryExtraAddress;

    private String deliveryDetailAddress;

    private String deliveryPhone;

    private String deliveryRequest;

    private User user;


    public OrdersResponseDto(Orders orders) {
        this.id = orders.getId();
        this.totalPrice = orders.getTotalPrice();
        this.date = orders.getDate();
        this.num = orders.getNum();
        this.ordererName = orders.getOrdererName();
        this.ordererEmail = orders.getOrdererEmail();
        this.ordererPhone = orders.getOrdererPhone();
        this.deliveryName = orders.getDeliveryName();
        this.deliveryPostalCode = orders.getDeliveryPostalCode();
        this.deliveryAddress = orders.getDeliveryAddress();
        this.deliveryExtraAddress = orders.getDeliveryExtraAddress();
        this.deliveryDetailAddress = orders.getDeliveryDetailAddress();
        this.deliveryPhone = orders.getDeliveryPhone();
        this.deliveryRequest = orders.getDeliveryRequest();
        this.user = orders.getUser();
    }
}
