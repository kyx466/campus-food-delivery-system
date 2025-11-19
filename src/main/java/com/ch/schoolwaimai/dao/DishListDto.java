package com.ch.schoolwaimai.dao;

import com.ch.schoolwaimai.entity.Dish;
import lombok.Data;

import java.math.BigDecimal;

// DishListDto.java
@Data
public class DishListDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl;
    private String description;
    private Integer status;

    public static DishListDto from(Dish dish) {
        DishListDto dto = new DishListDto();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setPrice(dish.getPrice());
        dto.setStock(dish.getStock());
        dto.setImageUrl(dish.getImageUrl());
        dto.setDescription(dish.getDescription());
        dto.setStatus(dish.getStatus());
        return dto;
    }
}