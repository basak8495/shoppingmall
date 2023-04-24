package com.project.shoppingmall.support;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerCommentRepository extends JpaRepository<CustomerComment, Long> {

    List<CustomerComment> findByCustomerBoard(CustomerBoard customerBoard);
}
