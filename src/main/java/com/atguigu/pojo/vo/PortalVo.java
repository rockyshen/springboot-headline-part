package com.atguigu.pojo.vo;

import lombok.Data;

/*
VO View Object
专门接收前端传过来的参数，现成实体类接不了的话，创建一个VO类去接

本类是首页传递的参数的VO类
前端会传递的JSON为：
{
  "keywords":"马斯克"，
  "type":0,
  "pageNum":1,
  "pageSize":10
}
 */

@Data
public class PortalVo {
    private String keyWords;

    private Integer type=0;

    private Integer pageNum=1;

    private Integer pageSize=10;
}
