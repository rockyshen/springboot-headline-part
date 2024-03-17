package com.atguigu.utils;

/*
常见状态码
 */
public enum ResultCodeEnum {
    SUCCESS(200,"success"),                 //每个常量都被看做enum类的一个实例
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(503,"passwordError"),
    NOTLOGON(504,"notlogin"),
    USERNAME_USED(505,"userNameUsed");

    private Integer code;
    private String message;

    //对于枚举构造函数，修饰符 "private "是多余的
    //枚举类的构造函数
    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
