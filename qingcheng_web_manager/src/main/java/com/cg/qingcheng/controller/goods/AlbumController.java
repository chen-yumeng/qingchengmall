package com.cg.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cg.qingcheng.entity.PageResult;
import com.cg.qingcheng.entity.Result;
import com.cg.qingcheng.pojo.goods.Album;
import com.cg.qingcheng.service.goods.AlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: qingcheng_parent->AlbumController
 * @description:
 * @author: cg
 * @create: 2020-02-21 14:24
 **/
@RestController
@ResponseBody
@RequestMapping("/album")
public class AlbumController {

    @Reference
    private AlbumService albumService;

    @GetMapping("/findAll")
    public List<Album> findAll() {
        return albumService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Album> findPage(int page, int size) {
        return albumService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Album> findList(@RequestBody Map<String, Object> searchMap) {
        return albumService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Album> findPage(@RequestBody Map<String, Object> searchMap, int page, int size) {
        return albumService.findPage(searchMap, page, size);
    }

    @GetMapping("/findById")
    public Album findById(Long id) {
        return albumService.findById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Album album) {
        albumService.add(album);
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Album album) {
        albumService.update(album);
        return Result.ok();
    }

    @GetMapping("/delete")
    public Result delete(Long id) {
        albumService.delete(id);
        return Result.ok();
    }

}
