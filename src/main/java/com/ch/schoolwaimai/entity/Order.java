package com.ch.schoolwaimai.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 64)
    private String orderNo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "canteen_id")
    private Canteen canteen;

    private BigDecimal totalAmount;

    /** 0 待支付 1已支付 2制作中 3配送中 4已完成 5已取消 */
    private Integer status = 0;

    private String deliveryAddress;
    private String phone;
    private String note;

    @Column(updatable = false)
    private LocalDateTime createTime;

    private LocalDateTime payTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}