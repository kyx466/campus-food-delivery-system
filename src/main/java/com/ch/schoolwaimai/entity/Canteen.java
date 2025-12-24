package com.ch.schoolwaimai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "canteen")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Canteen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private Double latitude;
    private Double longitude;
    private String openTime;
    private String imageUrl;

    @Column(updatable = false)
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "canteen", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dish> dishes;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
    }
}