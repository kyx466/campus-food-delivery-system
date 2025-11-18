package com.ch.schoolwaimai.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class DishUpdateDto {
    @NotNull @Positive private BigDecimal price;
    @NotNull @PositiveOrZero private Integer stock;
    private String description;
    @NotNull @Min(0) @Max(1) private Integer status; // 0=下架 1=上架
}