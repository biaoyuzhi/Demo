package com.example.demo.controller;

import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wuzhihai
 */
class IndexControllerTest {

    @Test
    public void testAll() throws ParseException {
        Runnable myRunnable = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + "run");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        };

        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1),
                new TaskThreadFactory("aa-", true, 3), (r, executor) -> System.out.println("拒接策略"));

        for (int i = 0; i < 10; i++) {
            pool.execute(myRunnable);
        }
    }

}