package com.example.project.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date menuDate; // 菜单日期

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller; // 关联的商家

    @OneToMany(mappedBy = "menu")
    @JoinColumn(name = "dish_id")
    private List<Dish> dishes; // 菜单中的菜品列表

    // 构造函数
    public Menu() {
    }

    public Menu(Date menuDate, Seller seller, List<Dish> dishes) {
        this.menuDate = menuDate;
        this.seller = seller;
        this.dishes = dishes;
    }

    // getter 和 setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getMenuDate() {
        return menuDate;
    }

    public void setMenuDate(Date menuDate) {
        this.menuDate = menuDate;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}