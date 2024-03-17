package com.atguigu.test;

import com.atguigu.utils.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

/*
测试环境
 */

@SpringBootTest
public class JwtTest {
    // Field，依赖注入
    @Autowired
    private JwtHelper jwtHelper;

    @Test
    public void test01(){
        String token = jwtHelper.createToken(1L);
        System.out.println("token = " + token);

        int userId = jwtHelper.getUserId(token).intValue();
        System.out.println("userId = " + userId);

        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println("expiration = " + expiration);
    }



}
