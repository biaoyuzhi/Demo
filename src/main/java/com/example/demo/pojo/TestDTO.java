package com.example.demo.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class TestDTO {
    private Integer lineNumber;
    private String description;
    private Date reportDateFrom;
    private Date reportDateTo;
}
