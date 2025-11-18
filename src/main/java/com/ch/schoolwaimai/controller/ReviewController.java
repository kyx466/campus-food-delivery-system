package com.ch.schoolwaimai.controller;

import com.ch.schoolwaimai.dao.DishDao;
import com.ch.schoolwaimai.dao.OrderDao;
import com.ch.schoolwaimai.dao.ReviewDao;
import com.ch.schoolwaimai.dao.UserDao;
import com.ch.schoolwaimai.dto.ReviewCreateDto;
import com.ch.schoolwaimai.entity.Dish;
import com.ch.schoolwaimai.entity.Order;
import com.ch.schoolwaimai.entity.Review;
import com.ch.schoolwaimai.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewDao reviewDao;
    private final OrderDao orderDao;
    private final DishDao dishDao;
    private final UserDao userDao;

    @PostMapping
    public Review createReview(@RequestBody ReviewCreateDto dto) {
        User user = userDao.findById(dto.getUserId()).orElseThrow();
        Dish dish = dishDao.findById(dto.getDishId()).orElseThrow();
        Order order = orderDao.findById(dto.getOrderId()).orElseThrow();

        Review review = new Review();
        review.setUser(user);
        review.setDish(dish);
        review.setOrder(order);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        return reviewDao.save(review);
    }

    @GetMapping("/dishes/{dishId}")
    public List<Review> getReviewsByDish(@PathVariable Long dishId) {
        return reviewDao.findByDishIdOrderByCreateTimeDesc(dishId);
    }
}