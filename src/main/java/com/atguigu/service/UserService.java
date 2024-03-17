package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 61750
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-03-15 09:57:49
*/
public interface UserService extends IService<User> {

    Result login(User user);    //抽象方法，登录业务

    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);
}
