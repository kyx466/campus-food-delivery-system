package com.ch.schoolwaimai.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDto {
    @NotNull
    private Long userId;

    @NotEmpty
    private List<OrderItemDto> items;

    private String deliveryAddress;
    private String phone;
    private String note;

    @Data
    public static class OrderItemDto {
        private Long dishId;
        private Integer quantity;
    }
}