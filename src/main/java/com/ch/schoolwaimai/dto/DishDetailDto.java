package com.ch.schoolwaimai.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DishDetailDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private String imageUrl;
    private Integer status;   // 1 上架 0 下架
    private String canteenName;   // 食堂名称
    private String categoryName;  // 分类名称
}