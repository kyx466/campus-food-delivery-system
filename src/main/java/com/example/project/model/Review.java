package com.example.project.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "review") // 可以根据实际表名调整
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 移除了重复的studentId和dishId字段，因为下面已经有关联字段
    private String comment;
    private Integer rating;
    private LocalDateTime reviewTime;

    @ManyToOne
    @JoinColumn(name = "studentId", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "dishId", insertable = false, updatable = false)
    private Dish dish;

    // 无参构造函数
    public Review() {
        this.reviewTime = LocalDateTime.now();
    }

    // 全参构造函数（不包含id）
    public Review(String comment, Integer rating, LocalDateTime reviewTime, Student student, Dish dish) {
        this.comment = comment;
        this.rating = rating;
        this.reviewTime = reviewTime != null ? reviewTime : LocalDateTime.now();
        this.student = student;
        this.dish = dish;
    }

    // 简化构造函数
    public Review(String comment, Integer rating, Student student, Dish dish) {
        this(comment, rating, LocalDateTime.now(), student, dish);
    }

    // getter 和 setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(LocalDateTime reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    // toString 方法（可选）
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                ", reviewTime=" + reviewTime +
                ", student=" + (student != null ? student.getIdstudent() : null) +
                ", dish=" + (dish != null ? dish.getId() : null) +
                '}';
    }
}