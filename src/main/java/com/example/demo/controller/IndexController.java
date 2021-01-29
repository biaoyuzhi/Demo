package com.example.demo.controller;

import com.example.demo.excel.annotation.ExcelExport;
import com.example.demo.excel.util.ResponseData;
import com.example.demo.pojo.TestDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class IndexController {

    @GetMapping()
    @ExcelExport()
    public ResponseData index(HttpServletRequest request, HttpServletResponse response) {
        List<TestDTO> list = new ArrayList() {    // 匿名内部类方式，注意下面的都是";"
            {
                add(new TestDTO(0, "a"));
                add(new TestDTO(10, "b"));
                add(new TestDTO(20, "c"));
            }
        };
        return new ResponseData(list);
    }

}
