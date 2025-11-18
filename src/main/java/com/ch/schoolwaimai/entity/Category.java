package com.ch.schoolwaimai.entity;

import com.ch.schoolwaimai.entity.Canteen;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "canteen_id")
    private Canteen canteen;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Dish> dishes;
}