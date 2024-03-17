package com.atguigu.service;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 61750
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-03-15 09:57:49
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalVo portalVo);   // 自定义的分页查询方法

    Result showHeadlineDetail(Integer hid);

    Result publish(Headline headline,String token);

    Result updateData(Headline headline);
}
