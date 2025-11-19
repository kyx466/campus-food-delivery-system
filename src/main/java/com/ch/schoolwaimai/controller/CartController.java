package com.ch.schoolwaimai.controller;

import com.ch.schoolwaimai.dto.AddToCartDto;
import com.ch.schoolwaimai.dto.CartItemDto;
import com.ch.schoolwaimai.dto.UpdateCartDto;
import com.ch.schoolwaimai.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartDto dto) {
        Long userId = 1L; // 模拟用户ID
        cartService.addToCart(userId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/items")
    public List<CartItemDto> getCartItems() {
        Long userId = 1L;
        return cartService.getCartItems(userId);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody UpdateCartDto dto) {
        Long userId = 1L;
        cartService.updateCartItemQuantity(userId, dto.getDishId(), dto.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item/{dishId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long dishId) {
        Long userId = 1L;
        cartService.removeFromCart(userId, dishId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        Long userId = 1L;
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}