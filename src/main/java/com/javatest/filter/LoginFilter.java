package com.javatest.filter;

import com.javatest.exception.MyException;
import com.javatest.util.HttpRequestWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ContentType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: azure
 **/
@WebFilter(urlPatterns = {"/*"}, filterName = "loginFilter")
@Slf4j
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @SneakyThrows(MyException.class)
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpRequestWrapper wrapper = null;

        // 排除option请求
        if ("OPTIONS".equals(req.getMethod())) {
            chain.doFilter(request, response);
            return;
        }

        String body = null;

        // 只针对application/json格式的进行封装
        if (req.getContentType().equals(ContentType.APPLICATION_JSON.getMimeType())) {
            wrapper = new HttpRequestWrapper(req);
        }

        if (wrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(wrapper,response);

            // 即便通过controller和切面，返回到过滤器照样可以通过getBody方法重新获取缓存的入参
            body = IOUtils.toString(wrapper.getBody(),request.getCharacterEncoding());
            System.out.println("filter-body:"+body);

        }

    }

    @Override
    public void destroy() {

    }
}