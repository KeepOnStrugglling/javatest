package com.javatest.config;

import com.javatest.webservice.server.impl.JAXTestServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.xml.ws.Endpoint;

/**
 * 使用cxf发布webservice服务，实现同一端口同时提供API和webservice
 */
@Configuration
public class WebServiceConfig implements WebMvcConfigurer {

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    @Bean
    public JAXTestServiceImpl JAXTestService() {
        return new JAXTestServiceImpl();
    }
    @Bean
    public Endpoint endpointForResources() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), JAXTestService());
        endpoint.publish("/getJAX");
        return endpoint;
    }
}
