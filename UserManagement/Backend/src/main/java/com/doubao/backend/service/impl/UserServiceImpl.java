package com.doubao.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doubao.backend.model.User;
import com.doubao.backend.service.UserService;
import com.doubao.backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author Lenovo
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-02-29 23:56:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {

        final String salt = "doubao";

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

        return 0;
    }
}




