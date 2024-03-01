package com.doubao.backend.service;
import java.util.Date;

import com.doubao.backend.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void test(){
        User user = new User();
        user.setUsername("testUser");
        user.setUserAccount("123");
        user.setAvatarUrl("asdfadf");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("123");
        user.setEmail("123");
        user.setUserRole(0);
        user.setPlanetCode("");
        boolean re = userService.save(user);
        System.out.println(user.getId());
        assertTrue(re);
    }

}