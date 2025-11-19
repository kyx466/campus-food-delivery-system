package com.ch.schoolwaimai.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// AddToCartDto.java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {
    @NotNull
    private Long dishId;
    @NotNull
    @Min(1)
    private Integer quantity = 1;
}
