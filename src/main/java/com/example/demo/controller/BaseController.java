package com.example.demo.controller;

import com.example.demo.config.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author wuzhihai 处理前端传递的字符类型时间参数。只需继承该BaseController类即可
 */
@RestController
public class BaseController {
    @InitBinder
    public void initBinder(WebDataBinder dataBinder, HttpServletRequest request) {
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor());
    }
}
