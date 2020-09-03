package com.javatest;

import com.javatest.config.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@ComponentScan(basePackages = "com.javatest")
@MapperScan("com.javatest.mapper")
@ServletComponentScan
//@ImportResource(locations = { "classpath:config/cxf-config.xml" })  // cxf提供restful服务的配置引入
@EnableScheduling
public class JavaTestApplication implements ApplicationListener<ContextRefreshedEvent> {
    public static void main(String[] args) {
        SpringApplication.run(JavaTestApplication.class);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        SpringContextUtil.setApplicationContext(event.getApplicationContext());
    }
}
