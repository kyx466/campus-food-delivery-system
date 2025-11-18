package com.ch.schoolwaimai.dao;

import com.ch.schoolwaimai.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    /* 按用户倒序查订单 */
    List<Order> findByUserIdOrderByCreateTimeDesc(Long userId);
}