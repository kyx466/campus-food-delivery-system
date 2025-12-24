package com.ch.schoolwaimai.dao;

import com.ch.schoolwaimai.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DishDao extends JpaRepository<Dish, Long> {
    // 按食堂 ID 查菜品
    List<Dish> findByCanteenId(Long canteenId);
    List<Dish> findByCategoryId(Long categoryId);
    List<Dish> findByNameContaining(String name);
}