package com.ch.schoolwaimai.controller;

import com.ch.schoolwaimai.dao.DishListDto;
import com.ch.schoolwaimai.dto.DishCreateDto;
import com.ch.schoolwaimai.dto.DishDetailDto;
import com.ch.schoolwaimai.dto.DishUpdateDto;
import com.ch.schoolwaimai.entity.Dish;
import com.ch.schoolwaimai.service.DishService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    @GetMapping
    public List<Dish> list() {
        return dishService.listAll();
    }
    @GetMapping("/canteens/{canteenId}/dishes")
    public List<Dish> listByCanteen(@PathVariable Long canteenId) {
        return dishService.listByCanteen(canteenId);
    }
    @PostMapping
    public Dish create(@Valid @RequestBody DishCreateDto dto) {
        return dishService.save(dto);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        dishService.deleteById(id);
    }
    @GetMapping("/categories/{categoryId}")
    public List<DishListDto> listByCategory(@PathVariable Long categoryId) {
        List<Dish> dishes = dishService.listByCategory(categoryId);
        return dishes.stream()
                .map(DishListDto::from)
                .collect(Collectors.toList());
    }
    @PutMapping("/{id}")
    public Dish update(@PathVariable Long id,
                       @Valid @RequestBody DishUpdateDto dto) {
        return dishService.update(id, dto);
    }
    @PostMapping("/{id}/image")
    public Dish uploadImage(@PathVariable Long id,
                            @RequestParam("image") MultipartFile image) {
        System.out.println("=== Controller 被调用 ===");
        System.out.println("菜品ID: " + id);
        System.out.println("文件名: " + image.getOriginalFilename());
        System.out.println("文件大小: " + image.getSize());
        System.out.println("文件类型: " + image.getContentType());
        return dishService.updateImage(id, image);
    }
    @GetMapping("/search")
    public List<Dish> searchByName(@RequestParam String name) {
        return dishService.searchByName(name);

    }

    @GetMapping("/{id}/detail")
    public DishDetailDto detail(@PathVariable Long id) {
        return dishService.getDetail(id);
    }

}
