package com.project.shoppingmall.support;

import com.project.shoppingmall.account.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerBoardRequestDto {

    private String title;

    private String content;

    private String deleteYn;

    private User user;


    public CustomerBoard dtoToEntity() {
        CustomerBoard customerBoard = CustomerBoard.builder()
                .title(title)
                .content(content)
                .deleteYn("N")
                .user(user)
                .build();
        return customerBoard;
    }
}
