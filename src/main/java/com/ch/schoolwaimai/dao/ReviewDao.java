package com.ch.schoolwaimai.dao;

import com.ch.schoolwaimai.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDao extends JpaRepository<Review, Long> {
    /* 按菜品倒序查评价 */
    List<Review> findByDishIdOrderByCreateTimeDesc(Long dishId);
}