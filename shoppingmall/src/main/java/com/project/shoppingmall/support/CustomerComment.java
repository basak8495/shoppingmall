package com.project.shoppingmall.support;

import com.project.shoppingmall.account.User;
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
public class CustomerComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "customer_board_id")
    private CustomerBoard customerBoard;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
