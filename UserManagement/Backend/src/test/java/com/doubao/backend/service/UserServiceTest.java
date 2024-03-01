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
    public void testAdd(){
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

    @Test
    public void testRegister(){
        String userAccount = "testUser";
        String userPassword = "";
        String checkPassword = "1";
        long re = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, re);
        userAccount = "";
        userPassword = "12345678";
        checkPassword = "1";
        re = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, re);
        userAccount = "testUser";
        userPassword = "!12345678";
        checkPassword = "!12345678";
        re = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, re);
        userAccount = "testUser";
        userPassword = "12345678";
        checkPassword = "1";
        re = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, re);
        userAccount = "!testUser";
        userPassword = "12345678";
        checkPassword = "12345678";
        re = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, re);
        userAccount = "testUser";
        userPassword = "12345678";
        checkPassword = "1";
        re = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, re);
        userAccount = "testUser";
        userPassword = "12345678";
        checkPassword = "12345678";
        re = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, re);
        userAccount = "testUser2";
        userPassword = "12345678";
        checkPassword = "12345678";
        re = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(0, re);
    }

}