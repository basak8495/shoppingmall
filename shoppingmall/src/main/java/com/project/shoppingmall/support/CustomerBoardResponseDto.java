package com.project.shoppingmall.support;

import com.project.shoppingmall.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CustomerBoardResponseDto {

    private Long id;

    private String title;

    private String content;

    private String deleteYn;

    private User user;


    public CustomerBoardResponseDto(CustomerBoard customerBoard) {
        this.id = customerBoard.getId();
        this.title = customerBoard.getTitle();
        this.content = customerBoard.getContent();
        this.deleteYn = customerBoard.getDeleteYn();
        this.user = customerBoard.getUser();
    }
}
