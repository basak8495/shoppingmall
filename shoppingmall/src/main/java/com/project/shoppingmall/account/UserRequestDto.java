package com.project.shoppingmall.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String username;

    private String password;

    private String name;

    private String email;

    private String postalCode;

    private String address;

    private String extraAddress;

    private String detailAddress;

    private String phone;

    private String deleteYn;

    private Boolean enabled;


    public User toEntity() {
        User user = User.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .postalCode(postalCode)
                .address(address)
                .extraAddress(extraAddress)
                .detailAddress(detailAddress)
                .phone(phone)
                .deleteYn("N")
                .enabled(enabled)
                .build();
        return user;
    }
}
