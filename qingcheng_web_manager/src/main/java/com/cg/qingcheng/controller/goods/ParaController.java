package com.cg.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.entity.Result;
import com.cg.qingcheng.pojo.goods.Para;
import com.cg.qingcheng.service.goods.ParaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: qingcheng_parent->ParaController
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:24
 **/
@RestController
@RequestMapping("/para")
public class ParaController {

    @Reference
    private ParaService paraService;

    @GetMapping("/findAll")
    public List<Para> findAll() {
        return paraService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Para> findPage(int page, int size) {
        return paraService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Para> findList(@RequestBody Map<String, Object> searchMap) {
        return paraService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Para> findPage(@RequestBody Map<String, Object> searchMap, int page, int size) {
        return paraService.findPage(searchMap, page, size);
    }

    @GetMapping("/findById")
    public Para findById(Integer id) {
        return paraService.findById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Para para) {
        paraService.add(para);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Para para) {
        paraService.update(para);
        return Result.ok();
    }

    @GetMapping("/delete")
    public Result delete(Integer id) {
        paraService.delete(id);
        return Result.ok();
    }

    @GetMapping("/findParaByTemplateId")
    public List<Para> findParaByTemplateId(String id) {
        return paraService.findParaByTemplateId(id);
    }

}
