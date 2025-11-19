package com.ch.schoolwaimai.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartDto {
    @NotNull
    private Long dishId;

    @NotNull
    @Min(0)
    private Integer quantity;
}