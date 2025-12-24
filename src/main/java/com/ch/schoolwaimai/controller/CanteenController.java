package com.ch.schoolwaimai.controller;

import com.ch.schoolwaimai.entity.Canteen;
import com.ch.schoolwaimai.dao.CanteenDao;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/canteens")
@RequiredArgsConstructor
public class CanteenController {

    private final CanteenDao canteenDao;

    @GetMapping
    public List<Canteen> list() {
        return canteenDao.findAll();
    }
}