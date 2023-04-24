package com.project.shoppingmall.orders;

import com.project.shoppingmall.account.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUser(User user);
}
