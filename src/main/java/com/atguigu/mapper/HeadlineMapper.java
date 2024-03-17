package com.atguigu.mapper;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
* @author 61750
* @description 针对表【news_headline】的数据库操作Mapper
* @createDate 2024-03-15 09:57:49
* @Entity com.atguigu.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {

    //注意1:查询需要自定义语句，自定义mapper方法，携带分页，mybatis_plus提供的方法用不了了！
    IPage<Map> selectMyPage(IPage page, @Param("portalVo") PortalVo portalVo);

    Map queryDetailMap(Integer hid);
    // 这个@Param注解是mybatis用的，不是SpringMVC的从param接收参数的注解是@RequestParam
    // #{对象的Field} 也就是 #{ 实体类的Field }
    // @Param注解，可以通过key取到VO对象的属性
}




