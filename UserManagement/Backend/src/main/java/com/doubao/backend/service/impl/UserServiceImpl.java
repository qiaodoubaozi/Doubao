package com.doubao.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doubao.backend.mapper.UserMapper;
import com.doubao.backend.model.User;
import com.doubao.backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


/**
 * @author Lenovo
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-02-29 23:56:08
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final String salt = "doubao";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        // 账号密码不能为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }

        // 账号格式校验
        String accountPattern = "^[a-zA-Z0-9_]{4,16}$";
        if (!userAccount.matches(accountPattern)) {
            return -1;
        }

        // 密码格式校验
        String passwordPattern = "^[a-zA-Z0-9_]{8,16}$";
        if (!userPassword.matches(passwordPattern)) {
            return -1;
        }

        // 两次输入密码一致性校验
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((userPassword + salt).getBytes());

        // 创建用户(插入表单)
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }

        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        // 账号格式校验
        String accountPattern = "^[a-zA-Z0-9_]{4,16}$";
        if (!userAccount.matches(accountPattern)) {
            return null;
        }

        // 密码格式校验
        String passwordPattern = "^[a-zA-Z0-9_]{8,16}$";
        if (!userPassword.matches(passwordPattern)) {
            return null;
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((userPassword + salt).getBytes());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            log.info("user login failed, userAccount can not be found or password is wrong");
            return null;
        }

        // 用户脱敏
        User safeUser = getSafeUser(user);

        // 保存登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);

        return safeUser;
    }

    @Override
    public User getSafeUser(User originUser) {
        if(originUser == null){
            return null;
        }
        User safeUser = new User();
        safeUser.setId(originUser.getId());
        safeUser.setUsername(originUser.getUsername());
        safeUser.setUserAccount(originUser.getUserAccount());
        safeUser.setAvatarUrl(originUser.getAvatarUrl());
        safeUser.setGender(originUser.getGender());
        safeUser.setPhone(originUser.getPhone());
        safeUser.setEmail(originUser.getEmail());
        safeUser.setUserStatus(originUser.getUserStatus());
        safeUser.setCreateTime(originUser.getCreateTime());
        safeUser.setUserRole(originUser.getUserRole());
        return safeUser;
    }

}




