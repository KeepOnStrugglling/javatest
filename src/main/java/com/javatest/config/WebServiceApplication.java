package com.javatest.config;

import com.javatest.webservice.server.impl.JAXTestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.xml.ws.Endpoint;

/**
 * 在不使用cxf等技术部署webservice服务的相关配置类。端口号与server.port不能相同
 */
//@Configuration(value = "/properties/config.properties")
public class WebServiceApplication implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger log = LoggerFactory.getLogger(WebServiceApplication.class);

    @Value("${wsdlUrl}")
    private String wsdlUrl;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Endpoint.publish(wsdlUrl,new JAXTestServiceImpl());
        log.info("webservice服务启动成功，wsdl地址：" + wsdlUrl + "?wsdl");
    }
}
