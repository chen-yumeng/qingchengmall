package com.cg.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.entity.Result;
import com.cg.qingcheng.pojo.goods.Template;
import com.cg.qingcheng.service.goods.TemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @program: qingcheng_parent->TemplateController
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:24
 **/
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Reference
    private TemplateService templateService;

    @GetMapping("/findAll")
    public List<Template> findAll() {
        return templateService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Template> findPage(int page, int size) {
        return templateService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Template> findList(@RequestBody Map<String, Object> searchMap) {
        return templateService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Template> findPage(@RequestBody Map<String, Object> searchMap, int page, int size) {
        return templateService.findPage(searchMap, page, size);
    }

    @GetMapping("/findById")
    public Template findById(Integer id) {
        return templateService.findById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Template template) {
        templateService.add(template);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Template template) {
        templateService.update(template);
        return Result.ok();
    }

    @GetMapping("/delete")
    public Result delete(Integer id) {
        templateService.delete(id);
        return Result.ok();
    }

}
