package com.atguigu.controller;

import com.atguigu.pojo.User;
import com.atguigu.service.HeadlineService;
import com.atguigu.service.UserService;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.ResolutionSyntax;

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HeadlineService headlineService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        return result;
    }

    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return result;
    }

    @PostMapping("checkUserName")
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return result;
    }

    @PostMapping("regist")
    public Result regist(@RequestBody User user) {
        Result result = userService.regist(user);
        return result;
    }

    @GetMapping("checkLogin")
    public Result checkLogin(@RequestHeader String token){
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration){
            return Result.build(null, ResultCodeEnum.NOTLOGON);
        }// 有效
        return Result.ok(null);
    }
}
