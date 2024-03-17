package com.atguigu.service.impl;

import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.Headline;
import com.atguigu.service.HeadlineService;
import com.atguigu.mapper.HeadlineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 61750
* @description 针对表【news_headline】的数据库操作Service实现
* @createDate 2024-03-15 09:57:49
*/
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private HeadlineMapper headlineMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result findNewsPage(PortalVo portalVo) {
        /*
        自定义的分页查询方法！
        1、进行分页数据查询；
        2、分页数据，拼接到result即可。

        注意1:查询需要自定义语句，自定义mapper方法(mapper.xml中写sql)，携带分页，mybatis_plus提供的方法用不了了！
        注意2：返回的结果List<Map>，因为每一个数据没有对应的类可以放，就装Map里
        */
        IPage<Map> page = new Page<>(portalVo.getPageNum(),portalVo.getPageSize());
        headlineMapper.selectMyPage(page,portalVo);
        List<Map> records =  page.getRecords();

        //封装结果
        Map data = new HashMap();
        data.put("pageData",records);
        data.put("pageNum",page.getCurrent());
        data.put("pageSize",page.getSize());
        data.put("totalPage",page.getPages());
        data.put("totalSize",page.getTotal());

        Map pageInfo = new HashMap();
        pageInfo.put("pageInfo",data);

        return Result.ok(pageInfo);
    }

    @Override
    public Result showHeadlineDetail(Integer hid) {
        /*
        1、查询对应的数据（多表查询：headline + user)，返回map
        2、阅读量+1【第一步查询乐观锁】
         */

        Map data = headlineMapper.queryDetailMap(hid);
        Map headlineMap = new HashMap();
        headlineMap.put("headline",data);


        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
        headline.setVersion((Integer) data.get("version"));
        headline.setPageViews((Integer) data.get("pageViews") + 1);
        headlineMapper.updateById(headline);

        return Result.ok(headlineMap);

    }

    @Override
    public Result publish(Headline headline,String token) {
        /*
        1、补全数据
        2、数据装配
         */
        Integer userId = jwtHelper.getUserId(token).intValue();

        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);

        return Result.ok(null);
    }

    @Override
    public Result updateData(Headline headline) {
        /*
        注意亮点：
        1、根据hid，查询最新version
        2、更新update时间的字段
         */
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();

        headline.setVersion(version);  //乐观锁

        headline.setUpdateTime(new Date());
        headlineMapper.updateById(headline);
        return Result.ok(null);
    }

}




