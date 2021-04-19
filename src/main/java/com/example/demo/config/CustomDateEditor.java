package com.example.demo.config;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wuzhihai
 */
public class CustomDateEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Date date = this.str2Date(text, null);
        setValue(date);
    }

    /**
     * 把一个字符串转换为指定的日期格式,默认是yyyy-MM-dd
     *
     * @param strDate
     * @param formater
     * @return java.util.Date
     * @author mouse 2019-03-13 18:18
     */
    public Date str2Date(String strDate, String formater) {
        String localFormater = "yyyy-MM-dd";
        if (strDate == null) {
            return null;
        }
        if (formater != null) {
            localFormater = formater;
        }
        SimpleDateFormat df = new SimpleDateFormat(localFormater);
        Date date = new Date();
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            if (formater == null) {
                localFormater = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat dft = new SimpleDateFormat(localFormater);

                try {
                    date = dft.parse(strDate);
                } catch (ParseException pe2) {
                    try {
                        //可解析"Thu Apr 01 2021 00:00:00 GMT+0800"样式
                        date = new Date(strDate);
                    } catch (Exception pe3) {
                    }
                }
            }
        }
        return date;
    }
}
