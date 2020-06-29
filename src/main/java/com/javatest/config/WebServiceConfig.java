package com.javatest.config;

import com.javatest.webservice.server.impl.CXFTestServiceImpl;
import com.javatest.webservice.server.impl.JAXTestServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
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
    // 利用cxf发布JAX的webservice接口和http接口
//    @Bean
//    public Endpoint endpointForResources() {
//        EndpointImpl endpoint = new EndpointImpl(springBus(), JAXTestService());
//        endpoint.publish("/getJAX");
//        return endpoint;
//    }

    // 发布cxf的webservice接口
    @Bean
    public CXFTestServiceImpl CXFTestService() {
        return new CXFTestServiceImpl();
    }

    // 修改拦截的路径，效果与在application.yml中配置cxf.path是一样的。如果两者都没配置，默认路径为/services/*。如果两者都配置了，以本配置类配置的为准。非必需项
    @Bean
    public ServletRegistrationBean newServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");
    }
    @Bean
    public Endpoint endpointForResources() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), CXFTestService());
        endpoint.publish("/getCXF");
        return endpoint;
    }
}
