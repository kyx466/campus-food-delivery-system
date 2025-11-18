package com.ch.schoolwaimai.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreateDto {
    @NotNull
    private Long userId;

    @NotNull
    private Long dishId;

    @NotNull
    private Long orderId;

    @Min(1) @Max(5)
    private Integer rating;

    private String comment;
}