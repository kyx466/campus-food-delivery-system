package com.ch.schoolwaimai.controller;

import com.ch.schoolwaimai.entity.Category;
import com.ch.schoolwaimai.dao.CategoryDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryDao categoryDao;

    @GetMapping
    public List<Category> listAll() {
        System.out.println("=== 接收到分类列表请求 ===");
        List<Category> categories = categoryDao.findAll();
        System.out.println("返回分类数量: " + categories.size());
        categories.forEach(c -> System.out.println("分类: " + c.getName()));
        return categories;
    }
}