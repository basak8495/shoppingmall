package com.project.shoppingmall.support;

import com.project.shoppingmall.account.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerBoardResonseDto {

    private String title;

    private String content;

    private String deleteYn;

    private User user;


    public CustomerBoardResonseDto(CustomerBoard customerBoard) {
        this.title = customerBoard.getTitle();
        this.content = customerBoard.getContent();
        this.deleteYn = customerBoard.getDeleteYn();
        this.user = customerBoard.getUser();
    }
}
