package com.cg.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.entity.Result;
import com.cg.qingcheng.pojo.goods.Sku;
import com.cg.qingcheng.service.goods.SkuService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @program: qingcheng_parent->SkuController
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:24
 **/
@RestController
@RequestMapping("/sku")
public class SkuController {

    @Reference
    private SkuService skuService;

    @GetMapping("/findAll")
    public List<Sku> findAll() {
        return skuService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Sku> findPage(int page, int size) {
        return skuService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Sku> findList(@RequestBody Map<String, Object> searchMap) {
        return skuService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Sku> findPage(@RequestBody Map<String, Object> searchMap, int page, int size) {
        return skuService.findPage(searchMap, page, size);
    }

    @GetMapping("/findById")
    public Sku findById(String id) {
        return skuService.findById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Sku sku) {
        skuService.add(sku);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Sku sku) {
        skuService.update(sku);
        return Result.ok();
    }

    @GetMapping("/delete")
    public Result delete(String id) {
        skuService.delete(id);
        return Result.ok();
    }

}
