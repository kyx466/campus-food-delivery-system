package com.example.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name="seller")
public class Seller {
    @Id
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String password;
    @OneToMany(mappedBy = "seller")
    private List<Dish> dishes;
    // 无参构造函数
    public Seller() {
    }

    public Seller(Long id, String name, String address, String phone, String password, List<Dish> dishes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }
public void setPassword(String password) {
        this.password = password;
}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}



