package com.javatest.schedule.scheduledExecutorService;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 使用ScheduledExecutorService
 * 为了方便展示，以http接口的方式提供调用的接口
 */
@RestController
@RequestMapping("/scheduledExecutor")
public class ScheduledExecutor {

    private static Logger log = LoggerFactory.getLogger(ScheduledExecutor.class);

    /**
     * ScheduledFuture<V> schedule(Runnable command, long delay, TimeUnit unit)
     * 创建并执行在给定延迟后启用的单次操作
     */
    @RequestMapping("/runnable")
    public void runnable(){
        // 这里用guava的ThreadFactoryBuilder创建线程工程
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("demo-%d").build();
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,factory);
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
                // 线程休眠3s模拟执行任务
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
                log.info("模拟执行任务成功");
            }
        }, 15, TimeUnit.SECONDS);
    }

    /**
     * ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
     *   创建并执行在给定的初始延迟之后，以给定的时间间隔执行周期性动作。
     */
    @RequestMapping("/runnableFixedRate")
    public void runnableFixedRate(){
        // 这里用org.apache.commons.lang3.concurrent.BasicThreadFactory创建线程工程，阿里推荐
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("demo-rate-%d").daemon(false).build());
        executorService.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
            // 线程休眠3s模拟执行任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
            log.info("模拟执行任务2成功");
        }, 15,5, TimeUnit.SECONDS); // 首次延迟15s，后按5s为周期执行
    }

    @RequestMapping("/runnableFixedDelay")
    public void runnableFixedDelay(){
        // 这里用org.apache.commons.lang3.concurrent.BasicThreadFactory创建线程工程，阿里推荐
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("demo-delay-%d").daemon(false).build());
        executorService.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
            // 线程休眠3s模拟执行任务
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":"+System.currentTimeMillis());
            log.info("模拟执行任务3成功");
        }, 15,5, TimeUnit.SECONDS); // 首次延迟15s，后按上次结束后5s为周期执行
    }
}
