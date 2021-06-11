package com.example.demo.service;

import com.example.demo.mapper.PersonMapper;
import com.example.demo.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuzhihai
 */
@Service
public class AllService {
    @Autowired
    private PersonMapper personMapper;

    @Transactional
    public void indexTest() {
        personMapper.updateNameById(1L, "qq");
        try {
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            System.err.println("休眠被打断");
        }
        System.out.println("休眠结束");
        Person person = personMapper.selectById(1L);
        System.out.println(person);
        throw new RuntimeException("手动抛出的异常");
    }

    public void indexTest2() {
        Person person = personMapper.selectById(1L);
        System.err.println("indexTest2: " + person);
    }
}
