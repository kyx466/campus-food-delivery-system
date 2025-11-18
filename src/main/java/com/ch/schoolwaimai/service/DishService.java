package com.ch.schoolwaimai.service;

import com.ch.schoolwaimai.dao.CanteenDao;
import com.ch.schoolwaimai.dao.CategoryDao;
import com.ch.schoolwaimai.dto.DishCreateDto;
import com.ch.schoolwaimai.dto.DishDetailDto;
import com.ch.schoolwaimai.dto.DishUpdateDto;
import com.ch.schoolwaimai.entity.Canteen;
import com.ch.schoolwaimai.entity.Category;
import com.ch.schoolwaimai.entity.Dish;
import com.ch.schoolwaimai.dao.DishDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishDao dishDao;
    private final CanteenDao canteenDao;   // 新增
    private final CategoryDao categoryDao; // 新增
    @Value("${file.upload-dir}")
    private String uploadDir;

    public List<Dish> listAll() {
        return dishDao.findAll();
    }
    public List<Dish> listByCanteen(Long canteenId) {
        return dishDao.findByCanteenId(canteenId);
    }

    public Dish save(DishCreateDto dto) {
        // 1. 查外键
        Canteen canteen = canteenDao.findById(dto.getCanteenId())
                .orElseThrow(() -> new IllegalArgumentException("食堂不存在"));
        Category category = categoryDao.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("分类不存在"));

        // 2. 构建实体
        Dish dish = new Dish();
        dish.setName(dto.getName());
        dish.setPrice(dto.getPrice());
        dish.setStock(dto.getStock());
        dish.setDescription(dto.getDescription());
        dish.setImageUrl(dto.getImageUrl());
        dish.setStatus(1);           // 默认上架
        dish.setCanteen(canteen);
        dish.setCategory(category);

        // 3. 保存
        return dishDao.save(dish);
    }

    public void deleteById(Long id) {
        dishDao.deleteById(id);
    }

    public List<Dish> listByCategory(Long categoryId) {
        return dishDao.findByCategoryId(categoryId);
    }
//新加查询方法
    public Dish findById(Long id) {
        return dishDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("菜品不存在"));
    }

    public Dish update(Long id, DishUpdateDto dto) {
        Dish dish = dishDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("菜品不存在"));

        dish.setPrice(dto.getPrice());
        dish.setStock(dto.getStock());
        dish.setDescription(dto.getDescription());
        dish.setStatus(dto.getStatus());

        return dishDao.save(dish);   // JPA 自动 UPDATE
    }



    public Dish updateImage(Long id, MultipartFile image) {
        System.out.println("=== 开始执行 updateImage 方法 ===");
        System.out.println("菜品ID: " + id);
        System.out.println("文件是否为空: " + image.isEmpty());
        System.out.println("文件名: " + image.getOriginalFilename());
        System.out.println("文件大小: " + image.getSize());
        System.out.println("文件类型: " + image.getContentType());

        // 1. 校验
        if (image.isEmpty()) {
            System.out.println("文件为空，抛出异常");
            throw new IllegalArgumentException("文件为空");
        }

        String contentType = image.getContentType();
        System.out.println("详细文件类型: " + contentType);

        if (contentType == null || !contentType.startsWith("image/")) {
            System.out.println("文件类型错误，抛出异常");
            throw new IllegalArgumentException("只允许图片");
        }

        // 2. 生成文件名
        String ext = contentType.substring(contentType.lastIndexOf('/') + 1);
        String fileName = "dish_" + id + "." + ext;
        System.out.println("生成文件名: " + fileName);

        // 3. 保存文件
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
        System.out.println("上传目录绝对路径: " + uploadPath);

        try {
            // 确保目录存在
            if (!Files.exists(uploadPath)) {
                System.out.println("创建上传目录...");
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            System.out.println("完整文件路径: " + filePath);

            // 保存文件
            System.out.println("开始写入文件...");
            Files.write(filePath, image.getBytes());
            System.out.println("文件保存成功!");

        } catch (IOException e) {
            System.err.println("文件保存失败: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("保存失败", e);
        }

        // 4. 更新数据库
        System.out.println("开始查询菜品...");
        Dish dish = dishDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("菜品不存在"));

        String imageUrl = "/food/images/" + fileName;
        System.out.println("设置图片URL: " + imageUrl);
        dish.setImageUrl(imageUrl);

        System.out.println("保存到数据库...");
        Dish savedDish = dishDao.save(dish);
        System.out.println("=== updateImage 方法完成 ===");

        return savedDish;
    }

    public List<Dish> searchByName(String name) {
        return dishDao.findByNameContaining(name);
    }



    public DishDetailDto getDetail(Long id) {
        Dish dish = dishDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("菜品不存在"));
        DishDetailDto dto = new DishDetailDto();
        dto.setId(dish.getId());
        dto.setName(dish.getName());
        dto.setPrice(dish.getPrice());
        dto.setStock(dish.getStock());
        dto.setDescription(dish.getDescription());

        // 返回完整 URL
        if (dish.getImageUrl() != null && dish.getImageUrl().startsWith("/")) {
            dto.setImageUrl("http://localhost:8080" + dish.getImageUrl());
        } else {
            dto.setImageUrl(dish.getImageUrl());
        }

        dto.setStatus(dish.getStatus());
        dto.setCanteenName(dish.getCanteen().getName());
        dto.setCategoryName(dish.getCategory().getName());
        return dto;
    }

}