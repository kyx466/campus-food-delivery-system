package com.ch.schoolwaimai.controller;

import com.ch.schoolwaimai.dao.UserDao;
import com.ch.schoolwaimai.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserDao userDao;

    @PostMapping("/login")
    public User login(@RequestParam String openid, @RequestParam String nickname, @RequestParam String avatarUrl) {
        return userDao.findByOpenid(openid).orElseGet(() -> {
            User user = new User();
            user.setOpenid(openid);
            user.setNickname(nickname);
            user.setAvatarUrl(avatarUrl);
            return userDao.save(user);
        });
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userDao.findById(id).orElseThrow(() -> new IllegalArgumentException("用户不存在"));
    }
}