package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.pojo.TestDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wuzhihai
 */
class IndexControllerTest {

    @Test
    public void testAll() throws ParseException {
        BigDecimal PaymentAmount = new BigDecimal("110");
        BigDecimal Amount = new BigDecimal("60.00");
        BigDecimal NeedPaymentAmount = new BigDecimal("110");

        BigDecimal singleAmount = PaymentAmount
                .multiply(Amount).divide(NeedPaymentAmount, 6);
        System.err.println(singleAmount);
    }

}