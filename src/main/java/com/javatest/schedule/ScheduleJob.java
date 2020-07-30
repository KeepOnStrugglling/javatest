package com.javatest.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author azure
 * @desc 使用@Scheduled注解实现定时调度
 */
@Component  // 注意要注入Spring容器中
public class ScheduleJob {

//    @Scheduled(cron = "0/10 * * * * ?")   // 每当整10秒执行一次
//    @Scheduled(fixedDelay = 5*1000)   // 上次执行完后5秒再执行
//    @Scheduled(initialDelay = 10*1000, fixedDelay = 5*1000)   // 首次执行在服务启动延迟10s执行，再按上次执行完后5秒执行
    @Scheduled(initialDelay = 10*1000, fixedRate = 5*1000)   // 首次执行在服务启动延迟10s执行，而后固定每隔5秒执行，无视任务执行需时
    public void test1(){
        try {
            System.out.println("进入任务");
            // 睡2秒模拟任务耗时
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("定时任务启动..." + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
