package com.project.shoppingmall.support;

import com.project.shoppingmall.account.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCommentRequestDto {

    private String comment;

    private Long customerBoardId;

    private CustomerBoard customerBoard;

    private User user;


    public CustomerComment dtoToEntity() {
        CustomerComment customerComment = CustomerComment.builder()
                .comment(comment)
                .customerBoard(customerBoard)
                .user(user)
                .build();
        return customerComment;
    }
}
