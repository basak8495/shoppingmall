package com.project.shoppingmall.support;

import com.project.shoppingmall.account.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCommentResponseDto {

    private String comment;

    private CustomerBoard customerBoard;

    private User user;


    public CustomerCommentResponseDto(CustomerComment customerComment) {
        this.comment = customerComment.getComment();
        this.customerBoard = customerComment.getCustomerBoard();
        this.user = customerComment.getUser();
    }
}
