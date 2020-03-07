package com.cg.qingcheng.controller;

import com.cg.qingcheng.entity.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: qingcheng_parent->BaseExceptionHandler
 * @description:
 * @author: cg
 * @create: 2020-02-21 20:14
 **/

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.build(202, "网络异常，请稍后再试!");
    }

}
