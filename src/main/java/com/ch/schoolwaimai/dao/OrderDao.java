package com.ch.schoolwaimai.dao;

import com.ch.schoolwaimai.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    /* 按用户倒序查订单 */
    List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);
    Optional<Order> findByUserIdAndStatus(Long userId, Integer status);
    Optional<Order> findByOrderNo(String orderNo);
}