package com.ch.schoolwaimai.dto;

import jakarta.validation.constraints.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public class DishCreateDto {
    @NotBlank private String name;
    @NotNull @Positive private BigDecimal price;
    @NotNull @PositiveOrZero private Integer stock;
    private String description;
    @NotNull private Long canteenId;
    @NotNull
    private Long categoryId;
    private String imageUrl;

    // 自动生成 Getter/Setter：按 Alt+Insert 选 Lombok 或手动
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getCanteenId() { return canteenId; }
    public void setCanteenId(Long canteenId) { this.canteenId = canteenId; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}