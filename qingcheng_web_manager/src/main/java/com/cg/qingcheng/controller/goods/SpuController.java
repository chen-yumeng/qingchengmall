package com.cg.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cg.qingcheng.entity.Goods;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.entity.Result;
import com.cg.qingcheng.pojo.goods.Spu;
import com.cg.qingcheng.pojo.goods.SpuCheck;
import com.cg.qingcheng.pojo.goods.SpuLog;
import com.cg.qingcheng.service.goods.SpuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: qingcheng_parent->SpuController
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:24
 **/
@RestController
@RequestMapping("/spu")
public class SpuController {

    @Reference
    private SpuService spuService;

    @GetMapping("/findAll")
    public List<Spu> findAll() {
        return spuService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Spu> findPage(int page, int size) {
        return spuService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Spu> findList(@RequestBody Map<String, Object> searchMap) {
        return spuService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Spu> findPage(@RequestBody Map<String, Object> searchMap, int page, int size) {
        return spuService.findPage(searchMap, page, size);
    }

    @GetMapping("/findById")
    public Spu findById(String id) {
        return spuService.findById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Spu spu) {
        spuService.add(spu);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Spu spu) {
        spuService.update(spu);
        return Result.ok();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        spuService.saveGoods(goods);
        return Result.ok();
    }

    @GetMapping("/findGoodsById")
    public Goods findGoodsById(String id) {
        return spuService.findGoodsById(id);
    }

    @PostMapping("/audit")
    public Result audit(@RequestBody Map<String, String> map) {
        spuService.audit(map.get("id"), map.get("status"), map.get("message"));
        return Result.ok();
    }

    @GetMapping("/pull")
    public Result pull(String id) {
        spuService.pull(id);
        return Result.ok();
    }

    @GetMapping("/put")
    public Result put(String id) {
        spuService.put(id);
        return Result.ok();
    }

    @GetMapping("/putMany")
    public Result putMany(String[] ids) {
        int i = spuService.putMany(ids);
        return Result.build(200, "上架" + i + "个商品");
    }

    @GetMapping("/pullMany")
    public Result pullMany(String[] ids) {
        int i = spuService.pullMany(ids);
        return Result.build(200, "下架" + i + "个商品");
    }

    @GetMapping("/delete")
    public Result delete(String id) {
        spuService.delete(id);
        return Result.ok();
    }

    @GetMapping("/completeDelete")
    public Result completeDelete(String id) {
        int i = spuService.completeDelete(id);
        return i != 0 ? Result.ok() : Result.build(202, "请先将该商品加入回收站!");
    }

    @GetMapping("/recover")
    public Result recover(String id) {
        int i = spuService.recover(id);
        return i != 0 ? Result.ok() : Result.build(202, "请先将该商品加入回收站!");
    }

    @GetMapping("/getTotal")
    public Map<String, Integer> getTotal() {
        Map<String, Integer> map = spuService.getTotal();
        return map;
    }

    @GetMapping("/getCheckLog")
    public List<SpuCheck> getCheckLog(String id) {
        return spuService.getCheckLog(id);
    }

    @GetMapping("/getSpuLog")
    public List<SpuLog> getSpuLog(String id) {
        return spuService.getSpuLog(id);
    }

}
