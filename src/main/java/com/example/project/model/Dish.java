package com.example.project.model;

import jakarta.persistence.*;

@Entity
@Table(name="dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;  // 菜品图片
    private Integer dailyStock;  // 每日库存
    private Boolean available;  // 是否可售
    @ManyToOne
    /*
    注解用于表示实体类之间存在多对一的关系。
    例如，在菜品（Dish）和菜品种类（DishCategory）的关系中，
    一个菜品种类可以包含多个菜品，但每个菜品只能属于一个菜品种类。
     */
    @JoinColumn(name = "categoryId")
    /*
    注解用于指定多对一关系中，外键列的名称。
    例如，在菜品（Dish）实体类中，@ManyToOne 注解表示菜品和菜品种类之间的关系是多对一的。
    @JoinColumn(name = "categoryId") 注解则指定了在菜品（Dish）表中，
    用于存储菜品种类（DishCategory）主键的列名为 categoryId
     */
    private DishCategory category;

    @ManyToOne
    @JoinColumn(name = "sellerId")
    private Seller seller;
    @ManyToOne
    @JoinColumn(name = "menuId")
    private Menu menu;  // 菜品属于某个菜单

    // 构造函数、getter 和 setter 方法
    public Dish() {
    }

    public Dish(String name, String description, Double price, DishCategory category,String imageUrl,int dailyStock, Boolean available,Seller seller) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.seller = seller;
        this.imageUrl = imageUrl;
        this.dailyStock = dailyStock;
        this.available = available;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getDailyStock() {
        return dailyStock;
    }
    public void setDailyStock(Integer dailyStock) {
        this.dailyStock = dailyStock;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public DishCategory getCategory() {
        return category;
    }

    public void setCategory(DishCategory category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}


