package com.doubao.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doubao.backend.common.BaseResponse;
import com.doubao.backend.common.ErrorCode;
import com.doubao.backend.utils.ResultUtils;
import com.doubao.backend.exception.BusinessException;
import com.doubao.backend.model.User;
import com.doubao.backend.model.request.UserLoginRequest;
import com.doubao.backend.model.request.UserRegisterRequest;
import com.doubao.backend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if(userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        Long user = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(user);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if(userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse logout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserService.USER_LOGIN_STATE);
        return ResultUtils.success(null);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute("userLoginState");
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "用户未登录");
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userId);
        User safeUser = userService.getSafeUser(user);
        return ResultUtils.success(safeUser);
    }

    @GetMapping("/page")
    public BaseResponse<IPage<User>> pageUsers(@RequestParam(defaultValue = "1") int current, @RequestParam(defaultValue = "10") int pageSize, HttpServletRequest request) {
        if(isNotAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH, "用户无权限");
        }
        Page<User> page = new Page<>(current, pageSize);
        Page<User> userPage = userService.page(page);
        List<User> safeUsers = userPage.getRecords().stream().map(user -> userService.getSafeUser(user)).toList();
        userPage.setRecords(safeUsers);
        return ResultUtils.success(userPage);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if(isNotAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> collect = userService.list(queryWrapper).stream().map(user -> userService.getSafeUser(user)).collect(Collectors.toList());
        return ResultUtils.success(collect);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if(isNotAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if(id < 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    private boolean isNotAdmin(HttpServletRequest request) {
        Object userLoginState = request.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        return userLoginState == null || ((User) userLoginState).getUserRole() != 1;
    }
}
