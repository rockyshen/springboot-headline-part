package com.atguigu.utils;

/*
全局统一返回结果类
 */

public class Result<T> {
    //Field
    private Integer code;

    private String message;

    private T data;      //返回数据，泛型！Type


    //无参构造函数，默认都有，不用特地写吧
    public Result(){}

    /*
    方法名：build
    形参：T data，返回的数据，任何数据类型

    protected权限
    <T>表示泛型方法
    Result<T>表示返回数据类型

    构建结果集时：
        将结果数据装到data字段
     */
    protected static <T> Result<T> build(T data) {
        Result<T> result =  new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    /*
    build方法的重载，
    如果传了结果数据、code、message
    构建结果集时：
        把三个都装到对应字段上
     */
    public static <T> Result<T> build(T body, Integer code, String message) {
        Result<T> result = build(body);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /*
    build方法的重载2
    如果传了结果数据、状态码枚举类对象
    构建结果集时：
        结果数据装到data字段
        枚举类对象调getCode装到code字段
        枚举类对象调getMessage装到message字段
     */
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    // 从枚举类对象上获取message，手动赋值给Field
    public Result<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    // 从枚举类对象上获取code，手动赋值给Field
    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }
    // TODO:上面三个重载的build方法，相当于三种传参方式，对应构造结果集的方式
    // TODO: ①只传data   ②传data、code、message   ③传data、状态码枚举类的对象



    /*
    操作成功
    @Param data baseCategoruList
    @param <T>
    @return
     */

    /*
    ok方法，传入结果数据，调用
     */
    public static <T> Result<T> ok(T data) {
//        Result<T> result = build(data);          // 这句话是不是多余的？
        return build(data, ResultCodeEnum.SUCCESS);
    }


    // getter 和 setter方法
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
