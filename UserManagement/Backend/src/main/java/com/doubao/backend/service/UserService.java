package com.doubao.backend.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.doubao.backend.model.User;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author Lenovo
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-02-29 23:56:08
*/
public interface UserService extends IService<User> {

    String USER_LOGIN_STATE = "userLoginState";

    long userRegister(String userAccount, String userPassword, String checkPassword);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    User getSafeUser(User originUser);
}
