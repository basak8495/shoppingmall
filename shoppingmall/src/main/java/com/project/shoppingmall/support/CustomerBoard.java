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
public class CustomerBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Column(name = "delete_yn")
    private String deleteYn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public CustomerBoard delete(String deleteYn) {
        this.deleteYn = deleteYn;
        return this;
    }
}
