package com.example.project.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`") // 使用反引号因为order是SQL关键字
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime orderTime;
    private String status;
    private Double totalAmount;

    // 与学生(Student)的多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId", referencedColumnName = "idstudent")
    private Student student;

    // 与商家(Seller)的多对一关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_sellerId", referencedColumnName = "id")
    private Seller seller;

    // 与配送信息(DeliveryInfo)的一对一关系
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DeliveryInfo deliveryInfo;

    // 与订单项的一对多关系
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    // 构造方法
    public Order() {
    }

    public Order(Student student, Seller seller, LocalDateTime orderTime, String status, Double totalAmount) {
        this.student = student;
        this.seller = seller;
        this.orderTime = orderTime;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DeliveryInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // 便捷方法 - 添加订单项
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    // 便捷方法 - 移除订单项
    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    // toString 方法
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", student=" + (student != null ? student.getIdstudent() : null) +
                ", seller=" + (seller != null ? seller.getId() : null) +
                ", orderTime=" + orderTime +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                ", deliveryInfo=" + (deliveryInfo != null ? deliveryInfo.getIddeliveryinfo() : null) +
                '}';
    }
}