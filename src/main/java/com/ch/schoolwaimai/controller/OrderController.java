package com.ch.schoolwaimai.controller;

import com.ch.schoolwaimai.dao.CanteenDao;
import com.ch.schoolwaimai.dao.DishDao;
import com.ch.schoolwaimai.dao.OrderDao;
import com.ch.schoolwaimai.dao.UserDao;
import com.ch.schoolwaimai.dto.OrderCreateDto;
import com.ch.schoolwaimai.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderDao orderDao;
    private final DishDao dishDao;
    private final UserDao userDao;
    private final CanteenDao canteenDao;

    @PostMapping
    public Order createOrder(@RequestBody OrderCreateDto dto) {
        User user = userDao.findById(dto.getUserId()).orElseThrow();
        Canteen canteen = dishDao.findById(dto.getItems().get(0).getDishId()).orElseThrow().getCanteen();

        Order order = new Order();
        order.setUser(user);
        order.setCanteen(canteen);
        order.setOrderNo("ORDER_" + System.currentTimeMillis());
        order.setTotalAmount(dto.getItems().stream()
                .map(item -> dishDao.findById(item.getDishId()).orElseThrow().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setDeliveryAddress(dto.getDeliveryAddress());
        order.setPhone(dto.getPhone());
        order.setNote(dto.getNote());

        Order saved = orderDao.save(order);

        List<OrderItem> items = dto.getItems().stream().map(i -> {
            Dish dish = dishDao.findById(i.getDishId()).orElseThrow();
            OrderItem item = new OrderItem();
            item.setOrder(saved);
            item.setDish(dish);
            item.setQuantity(i.getQuantity());
            item.setPrice(dish.getPrice());
            return item;
        }).toList();

        saved.setOrderItems(items);
        return orderDao.save(saved);
    }

    @GetMapping("/users/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderDao.findByUserIdOrderByCreateTimeDesc(userId);
    }

    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        Order order = orderDao.findById(id).orElseThrow();
        order.setStatus(status);
        return orderDao.save(order);
    }
}