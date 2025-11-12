package com.example.project.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveryinfo")
public class DeliveryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iddeliveryinfo;

    private String deliveryAddress;
    private String deliveryStatus;
    private LocalDateTime deliveryTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deliverinfo_orderId", referencedColumnName = "idorder")
    private Order order;

    // 默认构造方法
    public DeliveryInfo() {
    }

    // 带参数的构造方法
    public DeliveryInfo(String deliveryAddress, String deliveryStatus, LocalDateTime deliveryTime, Order order) {
        this.deliveryAddress = deliveryAddress;
        this.deliveryStatus = deliveryStatus;
        this.deliveryTime = deliveryTime;
        this.order = order;
    }

    // 便捷构造方法 - 创建待配送的配送信息
    public DeliveryInfo(String deliveryAddress, Order order) {
        this.deliveryAddress = deliveryAddress;
        this.deliveryStatus = "PENDING"; // 默认状态为待处理
        this.deliveryTime = null; // 配送时间为空，等待实际配送
        this.order = order;
    }

    // 业务方法 - 更新配送状态
    public void updateStatus(String newStatus) {
        this.deliveryStatus = newStatus;
        if ("DELIVERED".equals(newStatus)) {
            this.deliveryTime = LocalDateTime.now();
        }
    }

    // 业务方法 - 检查是否可配送
    @Transient
    public boolean isDeliverable() {
        return deliveryAddress != null && !deliveryAddress.trim().isEmpty()
                && order != null;
    }

    // 业务方法 - 检查是否已配送
    @Transient
    public boolean isDelivered() {
        return "DELIVERED".equals(deliveryStatus) && deliveryTime != null;
    }

    // 业务方法 - 获取配送状态描述
    @Transient
    public String getStatusDescription() {
        switch (deliveryStatus) {
            case "PENDING": return "待接单";
            case "ACCEPTED": return "已接单";
            case "PICKED_UP": return "已取货";
            case "ON_THE_WAY": return "配送中";
            case "DELIVERED": return "已送达";
            case "CANCELLED": return "已取消";
            case "FAILED": return "配送失败";
            default: return "未知状态";
        }
    }

    // Getter 和 Setter 方法
    public int getIddeliveryinfo() {
        return iddeliveryinfo;
    }

    public void setIddeliveryinfo(int iddeliveryinfo) {
        this.iddeliveryinfo = iddeliveryinfo;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    // equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryInfo)) return false;
        DeliveryInfo that = (DeliveryInfo) o;
        return iddeliveryinfo == that.iddeliveryinfo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(iddeliveryinfo);
    }

    // toString 方法
    @Override
    public String toString() {
        return "DeliveryInfo{" +
                "iddeliveryinfo=" + iddeliveryinfo +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", order=" + (order != null ? order.getId() : null) +
                '}';
    }
}