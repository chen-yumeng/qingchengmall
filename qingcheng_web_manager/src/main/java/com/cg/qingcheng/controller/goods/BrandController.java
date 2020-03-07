package com.cg.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.entity.Result;
import com.cg.qingcheng.pojo.goods.Brand;
import com.cg.qingcheng.service.goods.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: qingcheng_parent->BrandController
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:24
 **/
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @GetMapping("/findAll")
    public Result findAll() {
        return Result.ok(brandService.findAll());
    }

    @GetMapping("/findByPage")
    public PageResult<Brand> findByPage(int page, int pageSize) {
        return brandService.findByPage(page, pageSize);
    }

    @PostMapping("/findListByNameAndLetter")
    public List<Brand> findListByNameAndLetter(@RequestBody Map searchMap) {
        return brandService.findListByNameAndLetter(searchMap);
    }

    @PostMapping("/findByPage")
    public PageResult<Brand> findByPageAndNameAndLetter(@RequestBody Map searchMap, int page, int pageSize) {
        return brandService.findByPageAndNameAndLetter(page, pageSize, searchMap);
    }

    @GetMapping("/findById")
    public Brand findById(Integer id) {
        return brandService.findById(id);
    }

    @PostMapping("/add")
    public Result addBrand(@RequestBody Brand brand) {
        brandService.addBrand(brand);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result updateBrande(@RequestBody Brand brand) {
        brandService.updateBrande(brand);
        return Result.ok();
    }

    @GetMapping("/deleteById")
    public Result deleteById(Integer id) {
        brandService.deleteById(id);
        return Result.ok();
    }

}
