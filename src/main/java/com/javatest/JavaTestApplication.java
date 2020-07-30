package com.javatest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@ComponentScan(basePackages = "com.javatest")
@MapperScan("com.javatest.dao")
@ServletComponentScan
//@ImportResource(locations = { "classpath:properties/cxf-config.xml" })  // cxf提供restful服务的配置引入
@EnableScheduling
public class JavaTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaTestApplication.class);
    }
}
