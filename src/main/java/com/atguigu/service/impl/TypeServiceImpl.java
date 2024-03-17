package com.atguigu.service.impl;

import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Type;
import com.atguigu.service.TypeService;
import com.atguigu.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 61750
* @description 针对表【news_type】的数据库操作Service实现
* @createDate 2024-03-15 09:57:49
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {
        /*
        查询所有类别数据
         */
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }
}




