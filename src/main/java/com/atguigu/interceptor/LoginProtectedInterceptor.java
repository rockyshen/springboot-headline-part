package com.atguigu.interceptor;

/*
登录保护拦截器，检查请求头中是否包含有效token
1、有token，有效，放行
2、没有token，无效，返回504
 */

import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        1、从请求头中获取token
        2、检查是否有效
         */
        String token = request.getHeader("token");
        boolean expiration = jwtHelper.isExpiration(token);

        if (!expiration) {
            //没过期
            return true;
        }
        Result result = Result.build(null, ResultCodeEnum.NOTLOGON);

        //TODO 将result转为JSON，写入response，这里没怎么写过，要记笔记
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);
        return false;
    }
}
