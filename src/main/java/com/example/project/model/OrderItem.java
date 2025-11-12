package com.example.project.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int orderItem_orderId;
    private int orderItem_dishId;
    private Integer quantity;
    private Double unitPrice;

    // 与Order实体的多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderItem_orderId", referencedColumnName = "idorder")
    private Order order;

    // 与菜品(Dish)实体的多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderItem_dishId", referencedColumnName = "id")
    private Dish dish;


    // 构造方法
    public OrderItem() {
    }

    public OrderItem(Order order, Dish dish, Integer quantity, Double unitPrice) {
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // 计算小计金额的便捷方法
    @Transient
    public Double getSubtotal() {
        if (unitPrice != null && quantity != null) {
            return unitPrice * quantity;
        }
        return 0.0;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    // equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        return id != null && id.equals(((OrderItem) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // toString 方法
    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + (order != null ? order.getId() : null) +
                ", dish=" + (dish != null ? dish.getId() : null) +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
    // 构造函数、getter 和 setter 方法
}