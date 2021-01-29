package com.example.demo.pojo;

import lombok.Data;

@Data
public class TestDTO {
    private Integer lineNumber;
    private String description;

    public TestDTO(Integer lineNumber, String description) {
        this.lineNumber = lineNumber;
        this.description = description;
    }
}
