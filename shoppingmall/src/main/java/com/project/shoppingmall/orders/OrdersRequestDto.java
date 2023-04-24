package com.project.shoppingmall.orders;

import com.project.shoppingmall.account.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersRequestDto {

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


    public Orders dtoToEntity() {
        Orders orders = Orders.builder()
                .totalPrice(totalPrice)
                .date(date)
                .num(num)
                .ordererName(ordererName)
                .ordererEmail(ordererEmail)
                .ordererPhone(ordererPhone)
                .deliveryName(deliveryName)
                .deliveryPostalCode(deliveryPostalCode)
                .deliveryAddress(deliveryAddress)
                .deliveryExtraAddress(deliveryExtraAddress)
                .deliveryDetailAddress(deliveryDetailAddress)
                .deliveryPhone(deliveryPhone)
                .deliveryRequest(deliveryRequest)
                .user(user)
                .build();
        return orders;
    }
}
