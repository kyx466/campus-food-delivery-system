package com.ch.schoolwaimai.service;

import com.ch.schoolwaimai.dao.*;
import com.ch.schoolwaimai.dto.AddToCartDto;
import com.ch.schoolwaimai.dto.CartItemDto;
import com.ch.schoolwaimai.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// CartService.java - 修改为使用订单系统
@Service
@RequiredArgsConstructor
public class CartService {
    private final OrderDao orderDao;
    private final DishDao dishDao;
    private final UserDao userDao; // 需要添加
    private final CanteenDao canteenDao; // 需要添加
    private final OrderItemDao orderItemDao; // 注入


    public void addToCart(Long userId, AddToCartDto dto) {
        // 获取用户和默认食堂（这里需要根据实际情况调整）
        User user = userDao.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        Canteen canteen = canteenDao.findById(1L) // 使用第一个食堂或根据菜品确定
                .orElseThrow(() -> new IllegalArgumentException("食堂不存在"));

        // 查找用户的待支付订单（购物车）
        Order cartOrder = orderDao.findByUserIdAndStatus(userId, 0)
                .orElseGet(() -> createNewCartOrder(user, canteen));

        // 检查菜品是否已在购物车
        Optional<OrderItem> existingItem = cartOrder.getOrderItems().stream()
                .filter(item -> item.getDish().getId().equals(dto.getDishId()))
                .findFirst();

        if (existingItem.isPresent()) {
            // 更新数量
            OrderItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        } else {
            // 添加新菜品
            Dish dish = dishDao.findById(dto.getDishId())
                    .orElseThrow(() -> new IllegalArgumentException("菜品不存在"));

            OrderItem newItem = OrderItem.builder()
                    .order(cartOrder)
                    .dish(dish)
                    .quantity(dto.getQuantity())
                    .price(dish.getPrice()) // 快照价格
                    .build();

            // 确保orderItems列表已初始化
            if (cartOrder.getOrderItems() == null) {
                cartOrder.setOrderItems(new ArrayList<>());
            }
            cartOrder.getOrderItems().add(newItem);
        }

        // 重新计算总金额
        updateOrderTotal(cartOrder);
        orderDao.save(cartOrder);
    }

    private Order createNewCartOrder(User user, Canteen canteen) {
        // 生成订单号
        String orderNo = "CART_" + System.currentTimeMillis();

        return Order.builder()
                .orderNo(orderNo)
                .user(user)  // 修正：使用user而不是userId
                .canteen(canteen) // 修正：添加canteen
                .status(0) // 待支付状态作为购物车
                .totalAmount(BigDecimal.ZERO)
                .orderItems(new ArrayList<>())
                .build();
    }

    private void updateOrderTotal(Order order) {
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            order.setTotalAmount(BigDecimal.ZERO);
            return;
        }

        BigDecimal total = order.getOrderItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(total);
    }

    public List<CartItemDto> getCartItems(Long userId) {
        Order cartOrder = orderDao.findByUserIdAndStatus(userId, 0).orElse(null);
        if (cartOrder == null || cartOrder.getOrderItems() == null || cartOrder.getOrderItems().isEmpty()) {
            return new ArrayList<>();
        }
        // ✅ 把日志加在这一行前面
        System.out.println(">>> getCartItems 查询，userId=" + userId);
        List<CartItemDto> result = cartOrder.getOrderItems().stream()
                .map(this::convertToCartItemDto)
                .collect(Collectors.toList());
        // ✅ 再加这一行
        System.out.println(">>> 返回条数=" + result.size());

        return cartOrder.getOrderItems().stream()
                .map(this::convertToCartItemDto)
                .collect(Collectors.toList());
    }

    private CartItemDto convertToCartItemDto(OrderItem item) {
        return CartItemDto.builder()
                .dishId(item.getDish().getId())
                .dishName(item.getDish().getName())
                .price(item.getPrice())
                .imageUrl(item.getDish().getImageUrl())
                .quantity(item.getQuantity())
                .subtotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .build();
    }

    public void removeFromCart(Long userId, Long dishId) {
        Order cartOrder = orderDao.findByUserIdAndStatus(userId, 0)
                .orElseThrow(() -> new IllegalArgumentException("购物车为空"));

        if (cartOrder.getOrderItems() == null) {
            return;
        }

        // 1. 找到要删除的 OrderItem 实体
        Optional<OrderItem> toRemove = cartOrder.getOrderItems().stream()
                .filter(item -> item.getDish().getId().equals(dishId))
                .findFirst();

        if (toRemove.isPresent()) {
            OrderItem item = toRemove.get();

            // ② 从列表移除
            cartOrder.getOrderItems().remove(item);

            // ③ 关键：直接从数据库删除这条记录
            orderItemDao.delete(item);

            // ④ 重新算总价
            updateOrderTotal(cartOrder);
            orderDao.save(cartOrder);

            System.out.println(">>> 删除后即将重新查询，dishId=" + dishId);
        }
    }


    public void updateCartItemQuantity(Long userId, Long dishId, Integer quantity) {
        if (quantity <= 0) {
            removeFromCart(userId, dishId);
            return;
        }

        Order cartOrder = orderDao.findByUserIdAndStatus(userId, 0)
                .orElseThrow(() -> new IllegalArgumentException("购物车为空"));

        if (cartOrder.getOrderItems() == null) {
            return;
        }

        cartOrder.getOrderItems().stream()
                .filter(item -> item.getDish().getId().equals(dishId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    updateOrderTotal(cartOrder);
                    orderDao.save(cartOrder);
                });
    }

    // 清空购物车（下单后）
    public void clearCart(Long userId) {
        Order cartOrder = orderDao.findByUserIdAndStatus(userId, 0).orElse(null);
        if (cartOrder != null) {
            orderDao.delete(cartOrder);
        }
    }
}