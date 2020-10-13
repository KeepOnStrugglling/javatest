package com.javatest.filter;

import com.javatest.util.JacksonUtil;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = "/python/*",filterName = "pythonfilter")
public class PythonFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("++++++++++++++++++启动python关键字过滤器+++++++++++++++++++");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("===============进入python关键字过滤器==============");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String script = request.getParameter("script");
        String[] scripts = script.split(System.lineSeparator());
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource("/config/blacklist.txt").getInputStream()));
        while ((line = br.readLine()) != null) {
            for (int i = 0; i < scripts.length; i++) {
                if (scripts[i].contains(line)) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter printWriter = response.getWriter();
                    Map<String,Object> jsonObject = new HashMap<>();
                    jsonObject.put("status", 4404); //自定义，前端要页面显示python代码包含敏感词
                    jsonObject.put("msg", String.format("第%d行出现敏感词:'%s'" ,i+1, line));
                    printWriter.append(JacksonUtil.obj2json(jsonObject));
                    return;
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        System.out.println("------------------关闭python关键字过滤器------------------");
    }
}
