package com.atguigu.controller;

import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.service.HeadlineService;
import com.atguigu.service.TypeService;
import com.atguigu.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("portal")
@CrossOrigin
public class PortalController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private HeadlineService headlineService;

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
        Result result = typeService.findAllTypes();
        return result;
    }

    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
        //根据前端提交的VO，查询头条文章
        Result result = headlineService.findNewsPage(portalVo);
        return result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }
}
