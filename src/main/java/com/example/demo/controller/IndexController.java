package com.example.demo.controller;

import com.example.demo.excel.util.ResponseData;
import com.example.demo.pojo.TestDTO;
import com.example.demo.service.AllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class IndexController extends BaseController {
    @Autowired
    private AllService service;

    @RequestMapping(value = "/export", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData index(TestDTO dto, HttpServletRequest request, HttpServletResponse response) {
        System.err.println(dto);
        List<TestDTO> list = new ArrayList();
        return new ResponseData(list);
    }

    @RequestMapping("/test")
    public String indexTest() {
        service.indexTest();
        return "OK";
    }

    @RequestMapping("/test2")
    public String indexTest2() {
        service.indexTest2();
        return "OK";
    }

}
