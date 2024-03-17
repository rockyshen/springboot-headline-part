package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author 61750
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-03-15 09:57:49
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result login(User user) {

        /*
        登录业务的实现步骤
        1、根据账号，查询用户对象 -> loginUser
        2、如果用户对象为空，表示账号错误，返回501
        3、对比密码：
            如果失败，则返回503错误
            如果成功，则生成基于id的token
        4、将token转到result结果集中，返回
         */

        //直接在service层利用mybatis_plus查数据库了，不走mapper层了
        //根据账号 查询数据
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());   //数据库的列值，user对象的属性
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);

        //账号没找到
        if (loginUser == null) {
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }

        //对比密码 user是传递过来的，loginUser是数据库里的
        if (!StringUtils.isEmpty(user.getUserPwd())    //传递的user的密码不为空，且等于数据库中的密码
                && MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())){
            //密码正确，登录成功
            //根据用户ID生成token，并将token封装到result返回
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            Map data = new HashMap<>();
            data.put("token",token);
            return Result.ok(data);
        }//else 密码错误
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    @Override
    public Result getUserInfo(String token) {
        /*
        1、token是否在有效期
        2、根据token，解析处用户id
        3、根据用户id,查询用户数据
        4、去掉密码，封装result结果返回
         */

        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null,ResultCodeEnum.NOTLOGON);
        }//else
        int userId = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userId);
        user.setUserPwd("");

        Map data = new HashMap();
        data.put("loginUser",user);

        return Result.ok(data);
    }

    @Override
    public Result checkUserName(String username) {
        /*
        1、根据账号查询
        2、如果count=0,表示可用
        3、如果count！=0，重名了不可用
         */
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);

        Long count = userMapper.selectCount(lambdaQueryWrapper);

        if (count == 0){
            return Result.ok(null);
        }//else
        return Result.build(null,ResultCodeEnum.USERNAME_USED);
    }

    @Override
    public Result regist(User user) {
        /*
        注册业务
        1、检查账号是否被注册
        2、密码从明文加密
        3、账号数据写入数据库
        4、返回结果
         */
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        Long count = userMapper.selectCount(lambdaQueryWrapper);
        if (count == 0){
            user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
            userMapper.insert(user);
            return Result.ok(null);
        }//else
        return Result.build(null,ResultCodeEnum.USERNAME_USED);
    }
}




