package com.cg.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.entity.Result;
import com.cg.qingcheng.pojo.goods.Category;
import com.cg.qingcheng.service.goods.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: qingcheng_parent->CategoryController
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:24
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Reference
    private CategoryService categoryService;

    @GetMapping("/findAll")
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Category> findPage(int page, int size) {
        return categoryService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Category> findList(@RequestBody Map<String, Object> searchMap) {
        return categoryService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Category> findPage(@RequestBody Map<String, Object> searchMap, int page, int size) {
        return categoryService.findPage(searchMap, page, size);
    }

    @GetMapping("/findById")
    public Category findById(Integer id) {
        return categoryService.findById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        categoryService.add(category);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.update(category);
        return Result.ok();
    }

    @GetMapping("/delete")
    public Result delete(Integer id) {
        categoryService.delete(id);
        return Result.ok();
    }

}
